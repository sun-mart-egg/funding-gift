package com.d201.fundingift.consumeralarm.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.consumeralarm.dto.request.PostConsumerAlarmRequest;
import com.d201.fundingift.consumeralarm.dto.response.GetConsumerAlarmResponse;
import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import com.d201.fundingift.consumeralarm.repository.ConsumerAlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.ALARM_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumerAlarmService {

    private final ConsumerAlarmRepository consumerAlarmRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void createAlarm(PostConsumerAlarmRequest request) {
        try {
            ConsumerAlarm alarm = ConsumerAlarm.from(request);
            consumerAlarmRepository.save(alarm);
            log.info("알람이 성공적으로 생성되었습니다: {}", alarm);
        } catch (Exception e) {
            log.error("알람 생성 중 오류 발생: ", e);
            throw new CustomException(ErrorType.ALARM_CREATION_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public List<GetConsumerAlarmResponse> getAlarmsByConsumerId() {
        Long consumerId = securityUtil.getConsumerId();
        try {
            List<ConsumerAlarm> alarms = consumerAlarmRepository.findByConsumerId(consumerId);
            return alarms.stream()
                    .map(GetConsumerAlarmResponse::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("사용자 ID로 알람 조회 중 오류 발생, 사용자 ID: {}: ", consumerId, e);
            throw new CustomException(ErrorType.ALARM_RETRIEVAL_FAILED);
        }
    }

    @Transactional
    public void updateAlarmReadStatus(String id) {
        try {
            ConsumerAlarm alarm = consumerAlarmRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ALARM_NOT_FOUND));
            alarm.setIsRead();
            consumerAlarmRepository.save(alarm);
            log.info("알람 읽음 상태가 성공적으로 업데이트되었습니다, ID: {}", id);
        } catch (Exception e) {
            log.error("알람 읽음 상태 업데이트 중 오류 발생, 알람 ID: {}: ", id, e);
            throw new CustomException(ErrorType.ALARM_UPDATE_FAILED);
        }
    }

    @Transactional
    public void deleteConsumerAlarm(String consumerAlarmId) {
        try {
            consumerAlarmRepository.deleteById(consumerAlarmId);
            log.info("알람이 성공적으로 삭제되었습니다, ID: {}", consumerAlarmId);
        } catch (Exception e) {
            log.error("알람 삭제 중 오류 발생, 알람 ID: {}: ", consumerAlarmId, e);
            throw new CustomException(ErrorType.ALARM_DELETION_FAILED);
        }
    }
    @Transactional
    public void deleteAlarmsByConsumerId() {
        Long consumerId = securityUtil.getConsumerId();
        try {
            List<ConsumerAlarm> alarms = consumerAlarmRepository.findByConsumerId(consumerId);
            alarms.forEach(alarm -> consumerAlarmRepository.deleteById(alarm.getConsumerAlarmId()));
            log.info("사용자 ID에 해당하는 모든 알람이 성공적으로 삭제되었습니다, 사용자 ID: {}", consumerId);
        } catch (Exception e) {
            log.error("사용자 ID로 알람 삭제 중 오류 발생, 사용자 ID: {}: ", consumerId, e);
            throw new CustomException(ErrorType.ALARM_DELETION_BY_USER_FAILED);
        }
    }
}
