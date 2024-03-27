package com.d201.fundingift.attendance.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.attendance.repository.AttendanceRepository;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.FundingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final FundingRepository fundingRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void postAttendance(PostAttendanceRequest postAttendanceRequest) {
        Consumer consumer = getConsumer();

        //펀딩 존재 여부, 펀딩 상태 확인
        Funding funding = getFunding(postAttendanceRequest.getFundingId());

        checkingFundingStatus(String.valueOf(funding.getFundingStatus()));

        /**
         * TODO : 결제 정보 관련 확인 해야함.
         */

        //펀딩한 금액 더하기
        funding.addSumPrice(postAttendanceRequest.getPrice());

        attendanceRepository.save(Attendance.from(postAttendanceRequest, consumer, funding));
    }

    private Consumer getConsumer() {
        return securityUtil.getConsumer();
    }

    private Funding getFunding(Long fundingId) {
        return fundingRepository.findByIdAndDeletedAtIsNull(fundingId)
                .orElseThrow(() -> new CustomException(ErrorType.FUNDING_NOT_FOUND));
    }

    private void checkingFundingStatus(String fundingStatus) {
        if("NOT_STARTED".equals(fundingStatus))
            throw new CustomException(ErrorType.FUNDING_NOT_STARTED);

        if("FUNDING_FINISHED".equals(fundingStatus))
            throw new CustomException(ErrorType.FUNDING_FINISHED);
    }

}
