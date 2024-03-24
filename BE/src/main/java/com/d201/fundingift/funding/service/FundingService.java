package com.d201.fundingift.funding.service;

import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift.funding.dto.request.PostFundingRequest;
import com.d201.fundingift.funding.entity.AnniversaryCategory;
import com.d201.fundingift.funding.entity.Funding;
import com.d201.fundingift.funding.repository.AnniversaryCategoryRepository;
import com.d201.fundingift.funding.repository.FundingRepository;
import com.d201.fundingift.product.entity.Product;
import com.d201.fundingift.product.entity.ProductOption;
import com.d201.fundingift.product.repository.ProductOptionRepository;
import com.d201.fundingift.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final AnniversaryCategoryRepository anniversaryCategoryRepository;


    @Transactional
    public void postFunding(PostFundingRequest postFundingRequest) {

        Consumer consumer = consumerRepository.findById(postFundingRequest.getConsumerId())
                .orElseThrow(() -> new CustomException(ErrorType.CONSUMER_NOT_FOUND));

        Product product = productRepository.findById(postFundingRequest.getProductId())
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_NOT_FOUND));

        ProductOption productOption = productOptionRepository.findById(postFundingRequest.getProductOptionId())
                .orElseThrow(() -> new CustomException(ErrorType.PRODUCT_OPTION_NOT_FOUND));

        AnniversaryCategory anniversaryCategory = anniversaryCategoryRepository.findById(postFundingRequest.getAnniversaryCategoryId())
                .orElseThrow(() -> new CustomException(ErrorType.ANNIVERSARY_CATEGORY_NOT_FOUND));

        isOver7Days(postFundingRequest.getStartDate(), postFundingRequest.getEndDate());

        fundingRepository.save(Funding.from(postFundingRequest, consumer, anniversaryCategory, product, productOption));
    }

    public void isOver7Days(LocalDate start, LocalDate end) {
        if (Math.abs(start.until(end).getDays()) > 7)
            throw new CustomException(ErrorType.FUNDING_DURATION_NOT_VALID);
    }
}
