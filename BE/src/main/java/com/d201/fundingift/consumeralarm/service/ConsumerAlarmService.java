package com.d201.fundingift.consumeralarm.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift.consumeralarm.dto.request.PostConsumerAlarmRequest;
import com.d201.fundingift.consumeralarm.dto.response.GetConsumerAlarmResponse;
import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import com.d201.fundingift.consumeralarm.repository.ConsumerAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.ALARM_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ConsumerAlarmService {

    private final ConsumerAlarmRepository consumerAlarmRepository;

    @Transactional
    public void createAlarm(PostConsumerAlarmRequest request) {
        consumerAlarmRepository.save(ConsumerAlarm.from(request));
    }

    @Transactional(readOnly = true)
    public List<GetConsumerAlarmResponse> getAlarmsByConsumerId(Long consumerId) {
        List<ConsumerAlarm> alarms = consumerAlarmRepository.findByConsumerId(consumerId);
        return alarms.stream()
                .map(GetConsumerAlarmResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAlarmReadStatus(Long id) {
        ConsumerAlarm alarm = consumerAlarmRepository.findById(id).orElseThrow(() -> new CustomException(ALARM_NOT_FOUND));
        alarm.setIsRead();
    }

    @Transactional
    public void deleteConsumerAlarm(Long consumerAlarmId) {
        consumerAlarmRepository.deleteById(consumerAlarmId);
    }

    @Transactional
    public void deleteAlarmsByConsumerId(Long consumerId) {
        List<ConsumerAlarm> alarms = consumerAlarmRepository.findByConsumerId(consumerId);
        for (ConsumerAlarm alarm : alarms) {
            consumerAlarmRepository.deleteById(alarm.getConsumerAlarmId());
        }
    }
}
