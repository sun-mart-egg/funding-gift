package com.d201.fundingift.product.service;

import com.d201.fundingift.product.dto.response.GetProductCategoryResponse;
import com.d201.fundingift.product.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductCategoryRepository productCategoryRepository;

    public List<GetProductCategoryResponse> getCategories() {
        return productCategoryRepository.findAll()
                .stream().map(GetProductCategoryResponse::from)
                .collect(Collectors.toList());
    }

}
