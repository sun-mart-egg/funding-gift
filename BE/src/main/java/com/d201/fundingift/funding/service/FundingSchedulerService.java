package com.d201.fundingift.funding.service;

import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.entity.status.FundingStatus;
import com.d201.fundingift.funding.repository.FundingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundingSchedulerService {

    private final FundingRepository fundingRepository;

    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") //실제 서비스용
    @Scheduled(cron = "0 8 2 * * ?", zone = "Asia/Seoul") //테스트용
    public void updateFundingStatusInProgress() {
        log.info("start updateFundingStatusInProgress");
        List<Funding> fundings = fundingRepository.findAllByFundingStatusAndDateAndDeletedAtIsNull(FundingStatus.PRE_PROGRESS, LocalDate.now());

        for(Funding f : fundings)
            f.changeStatus(String.valueOf(FundingStatus.IN_PROGRESS));
    }
}
