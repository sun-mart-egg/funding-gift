package com.d201.fundingift.attendance.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.attendance.repository.AttendanceRepository;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.friend.repository.FriendRepository;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.FundingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final FundingRepository fundingRepository;
    private final FriendRepository friendRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void postAttendance(PostAttendanceRequest postAttendanceRequest) {
        Consumer consumer = getConsumer();

        //펀딩 존재 여부
        Funding funding = getFunding(postAttendanceRequest.getFundingId());

        //펀딩 상태 확인
        checkingFundingStatus(String.valueOf(funding.getFundingStatus()));

        //펀딩 참여자의 친구목록에 펀딩 생성자가 있는지 확인
        checkingFriend(consumer.getId(), funding.getConsumer().getId());

        //펀딩 생성자가 본인의 친구만 참여 가능하도록 설정 하였는지 확인
        if(funding.getIsPrivate()) {
            //펀딩 참여자가 펀딩 생성자의 친구인지 확인
            checkingFriend(funding.getConsumer().getId(), consumer.getId());
        }

        //최소 금액 만족 확인
        checkingFundingMinPrice(funding.getMinPrice(), postAttendanceRequest.getPrice());

        //펀딩한 금액 더하기,목표 금액 달성시 상태 변경, 목표 금액 이상인 경우 예외
        checkingFundingTargetPrice(postAttendanceRequest.getPrice(), funding);

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
        if("PRE_PROGRESS".equals(fundingStatus))
            throw new CustomException(ErrorType.FUNDING_NOT_STARTED);

        if("SUCCESS".equals(fundingStatus) || "FAIL".equals(fundingStatus))
            throw new CustomException(ErrorType.FUNDING_FINISHED);
    }

    private void checkingFriend(Long consumerId, Long toConsumerId) {
        friendRepository.findById(consumerId + ":" + toConsumerId)
                .orElseThrow(() -> new CustomException(ErrorType.FRIEND_NOT_FOUND));
    }

    private void checkingFundingMinPrice(Integer minPrice, Integer price) {
        if(price < minPrice)
            throw new CustomException(ErrorType. FUNDING_NOT_VERIFY_MIN_PRICE);
    }

    private void checkingFundingTargetPrice(Integer price, Funding funding) {
        Integer targetPrice = funding.addSumPrice(price);

        if(targetPrice > funding.getTargetPrice())
            throw new CustomException(ErrorType.FUNDING_OVER_TARGET_PRICE);

        if(targetPrice.equals(funding.getTargetPrice()))
            funding.changeStatus("SUCCESS");
    }
}
