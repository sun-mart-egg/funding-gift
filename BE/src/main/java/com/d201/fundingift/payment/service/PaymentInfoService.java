package com.d201.fundingift.payment.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.attendance.repository.AttendanceRepository;
import com.d201.fundingift.payment.dto.request.PostPaymentInfoRequest;
import com.d201.fundingift.payment.entity.PaymentInfo;
import com.d201.fundingift.payment.entity.status.PaymentStatus;
import com.d201.fundingift.payment.repository.PaymentInfoRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentInfoService {

    private final AttendanceRepository attendanceRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final IamportClient iamportClient;
    private final SecurityUtil securityUtil;

    @Transactional
    public IamportResponse<Payment> postPaymentInfo(PostPaymentInfoRequest postPaymentInfoRequest) {
        Long myConsumerId = securityUtil.getConsumerId();
        try {
            //아엠포트 결제 단건 조회
            IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(postPaymentInfoRequest.getPaymentInfoUid());
            Attendance attendance = attendanceRepository.findById(postPaymentInfoRequest.getAttendanceId())
                    .orElseThrow(() -> new CustomException(ErrorType.ATTENDANCE_NOT_FOUND));

            // 결제 완료가 아니면
            if(!paymentIamportResponse.getResponse().getStatus().equals("paid")) {
                // 펀딩 참여 삭제
                attendanceRepository.delete(attendance);
                throw new CustomException(ErrorType.PAYMENT_FAIL);
            }

            //DB 펀딩 참여 금액
            Integer price = attendance.getPrice();

            //실 결제 금액
            Integer realPrice = paymentIamportResponse.getResponse().getAmount().intValue();

            if(!Objects.equals(price, realPrice)) {
                // 펀딩 참여 삭제
                attendanceRepository.delete(attendance);

                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(paymentIamportResponse.getResponse().getImpUid(), true, new BigDecimal(realPrice)));

                throw new CustomException(ErrorType.PAYMENT_FAIL);
            }

            PaymentInfo save = paymentInfoRepository.save(PaymentInfo.of(postPaymentInfoRequest.getPaymentInfoUid(), PaymentStatus.PAID, realPrice));

            attendance.getFunding().addSumPrice(price);
            attendance.updatePaymentInfo(save);
            attendance.updateDeletedAt(null);

            return paymentIamportResponse;
        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
