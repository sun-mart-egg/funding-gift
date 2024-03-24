package com.d201.fundingift._common.util;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 *  Security Context의 인증 객체로부터 다양한 정보를 뽑아서 제공하는 클래스
 */
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final ConsumerRepository consumerRepository;

    public Consumer getConsumer() {
        Long consumerId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new CustomException(ErrorType.CONSUMER_NOT_FOUND));
    }

}