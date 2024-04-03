package com.d201.fundingift.attendance.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.response.SliceList;
import com.d201.fundingift._common.util.FcmNotificationProvider;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.dto.request.UpdateAttendanceRequest;
import com.d201.fundingift.attendance.dto.response.GetAttendanceDetailResponse;
import com.d201.fundingift.attendance.dto.response.GetAttendancesResponse;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.attendance.repository.AttendanceRepository;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.friend.entity.Friend;
import com.d201.fundingift.friend.repository.FriendRepository;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.FundingRepository;
import com.d201.fundingift.notification.dto.FcmNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final FundingRepository fundingRepository;
    private final FriendRepository friendRepository;
    private final SecurityUtil securityUtil;
    private final FcmNotificationProvider fcmNotificationProvider;

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

        // 알림
        fcmNotificationProvider.send(funding.getConsumer().getId(),
                FcmNotificationDto.of("펀딩 참여 알림", consumer.getName() + "님이 펀딩에 참여했어요!"));
    }

    //펀딩 상세 조회의 펀딩 참여자 정보 리스트
    public SliceList<GetAttendancesResponse> getAttendancesResponse(Long fundingId, Pageable pageable) {
        Long myConsumerId = securityUtil.getConsumerId();

        //펀딩 존재 여부 확인
        Funding funding = getFunding(fundingId);

        //내 펀딩, (나의 친구 펀딩 + isPrivate false), (펀딩 생성자의 친한친구가 나 + isPrivate true)일 경우 상세 보기 가능
        if(checkingMyFunding(myConsumerId, funding.getConsumer().getId())) {
            return getMyAttendanceResponseSliceList(findAllByFundingId(funding.getId(), pageable));
        } else if(!funding.getIsPrivate() && checkingMyFriend(myConsumerId, funding.getConsumer().getId())) {
            return getMyAttendanceResponseSliceList(findAllByFundingId(funding.getId(), pageable));
        } else if(funding.getIsPrivate() && checkingIsFavoriteFriend(funding.getConsumer().getId(), myConsumerId)) {
            return getMyAttendanceResponseSliceList(findAllByFundingId(funding.getId(), pageable));
        }

        throw new CustomException(ErrorType.USER_UNAUTHORIZED);
    }

    //참여자 상세 정보 조회 - 펀딩 참여자나 펀딩 생성자만 상세 조회 가능
    public GetAttendanceDetailResponse getAttendanceDetailResponse(Long attendanceId, Long fundingId) {
        Long myConsumerId = securityUtil.getConsumerId();

        //펀딩 존재 여부 확인
        Funding funding = getFunding(fundingId);

        //펀딩 참여 존재 여부 확인
        Attendance attendance = getAttendance(attendanceId);

        //펀딩 참여 상세 정보 조회 권한 확인
        checkingAuthorizedAttendanceDetail(funding, myConsumerId, attendance);

        return GetAttendanceDetailResponse.from(attendance);
    }

    //펀딩 참여자에게 감사 메시지 작성하기 - 펀딩 생성자만 작성 가능
    @Transactional
    public void updateReceiveMessage(UpdateAttendanceRequest updateAttendanceRequest) {
        Long myConsumerId = securityUtil.getConsumerId();

        //펀딩 존재 여부 확인
        Funding funding = getFunding(updateAttendanceRequest.getFundingId());

        //펀딩 참여 존재 여부 확인
        Attendance attendance = getAttendance(updateAttendanceRequest.getAttendanceId());

        //감사 메시지 작성 권한 확인
        checkingAuthorizeWritingReceiveMessage(funding, myConsumerId);

        attendance.writingReceiveMessage(updateAttendanceRequest.getReceiveMessage());
    }

    /**
     * 내부 메서드
     */
    private static void checkingAuthorizedAttendanceDetail(Funding funding, Long myConsumerId, Attendance attendance) {
        log.info("myConsumerId={}, funding.getConsumer={}, attendance.getConsumer={}"
                , myConsumerId, funding.getConsumer().getId(), attendance.getConsumer().getId());
        if(!Objects.equals(funding.getConsumer().getId(), myConsumerId)
                && !Objects.equals(attendance.getConsumer().getId(), myConsumerId))
            throw new CustomException(ErrorType.USER_UNAUTHORIZED);
    }

    private Consumer getConsumer() {
        return securityUtil.getConsumer();
    }

    private Funding getFunding(Long fundingId) {
        return fundingRepository.findByIdAndDeletedAtIsNull(fundingId)
                .orElseThrow(() -> new CustomException(ErrorType.FUNDING_NOT_FOUND));
    }

    private SliceList<GetAttendancesResponse> getMyAttendanceResponseSliceList(Slice<Attendance> attendances) {
        return SliceList.from(attendances.stream().map(GetAttendancesResponse::from).collect(Collectors.toList()), attendances.getPageable(), attendances.hasNext());
    }

    private Slice<Attendance> findAllByFundingId(Long fundingId, Pageable pageable) {
        return attendanceRepository.findAllByFundingIdAndDeletedAtIsNull(fundingId,pageable);
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

    private boolean checkingMyFunding(Long myConsumerId, Long fundingConsumerId) {
        if(!Objects.equals(myConsumerId, fundingConsumerId))
            return false;

        return true;
    }

    private boolean checkingMyFriend(Long myConsumerId, Long fundingConsumerId) {
        if(friendRepository.findById(myConsumerId + ":" + fundingConsumerId).isEmpty())
            return false;
        return true;
    }

    private boolean checkingIsFavoriteFriend(Long fundingConsumerId, Long myConsumerId) {
        Optional<Friend> friend = friendRepository.findById(fundingConsumerId + ":" + myConsumerId);

        //보려는 펀딩 목록의 대상에 본인이 친구가 아니거나 친한 친구가 아닌 경우 -> false
        return friend.isPresent() && friend.get().getIsFavorite();
    }

    private Attendance getAttendance(Long attendanceId) {
        return attendanceRepository.findByIdAndDeletedAtIsNull(attendanceId)
                .orElseThrow(() -> new CustomException(ErrorType.ATTENDANCE_NOT_FOUND));
    }

    private static void checkingAuthorizeWritingReceiveMessage(Funding funding, Long myConsumerId) {
        if(!Objects.equals(funding.getConsumer().getId(), myConsumerId))
            throw new CustomException(ErrorType.USER_UNAUTHORIZED);
    }
}
