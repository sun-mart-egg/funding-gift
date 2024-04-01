package com.d201.fundingift.consumeralarm.repository;

import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConsumerAlarmRepository extends CrudRepository<ConsumerAlarm, Long> {
    List<ConsumerAlarm> findByConsumerId(Long consumerId);
    ConsumerAlarm findByConsumerAlarmId(Long ConsumerAlarmId);
}