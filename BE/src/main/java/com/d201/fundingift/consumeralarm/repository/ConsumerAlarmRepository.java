package com.d201.fundingift.consumeralarm.repository;

import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConsumerAlarmRepository extends CrudRepository<ConsumerAlarm, String> {
    List<ConsumerAlarm> findByConsumerId(Long consumerId);
    ConsumerAlarm findByConsumerAlarmId(String ConsumerAlarmId);
}