package com.d201.fundingift._common.util;

import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift._common.dto.FcmNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerService {

    private final ConsumerRepository consumerRepository;
    private final FcmNotificationProvider fcmNotificationProvider;

    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Seoul") // 초 분 시간 일 월 요일
    @Transactional
    public void postFundingAlarm() {
        log.info("[SchedulerService.postFundingAlarm]");

        // 7일 이내의 모든 날짜
        List<String> dates = getIn7Days();

        // 생일이 해당 날짜인 경우 알림 생성
        for (String date: dates) {
            log.info("date : {}", date);
            consumerRepository.findByBirthday(date).stream().iterator()
                .forEachRemaining(c -> fcmNotificationProvider.sendToOne(c.getId(),
                                    FcmNotificationDto.of("생일이 곧 다가와요.", c.getName() + "님! 펀딩을 만들어보세요."))
                );
        }
    }

    private List<String> getIn7Days() {
        List<String> dates = new ArrayList<>();

        for (int i=1; i<=7; i++) {
            LocalDate targetDate = LocalDate.now().plusDays(i);
            dates.add(targetDate.format(DateTimeFormatter.ofPattern("MMdd")));
        }

        return dates;
    }

}
