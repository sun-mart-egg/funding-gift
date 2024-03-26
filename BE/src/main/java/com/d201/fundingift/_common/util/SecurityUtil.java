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
        return findConsumerById(getConsumerIdOrElseThrow());
    }

    public Consumer getConsumerOrNull() {
        return findConsumerById(getConsumerIdOrElseNull());
    }

    private Long getConsumerIdOrElseThrow() {
        try {
            return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorType.USER_UNAUTHORIZED);
        }
    }

    private Long getConsumerIdOrElseNull() {
        try {
            return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Consumer findConsumerById(Long consumerId) {
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new CustomException(ErrorType.CONSUMER_NOT_FOUND));
    }
}
/*
* Todo
*  예외처리. 부탁.
*  consumerId만 뽑는 메소드. 필요.
*
*/