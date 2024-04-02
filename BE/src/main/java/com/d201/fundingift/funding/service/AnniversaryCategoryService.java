package com.d201.fundingift.funding.service;

import com.d201.fundingift.funding.dto.response.GetAnniversaryCategoryResponse;
import com.d201.fundingift.funding.entity.AnniversaryCategory;
import com.d201.fundingift.funding.repository.AnniversaryCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnniversaryCategoryService {

    private final AnniversaryCategoryRepository anniversaryCategoryRepository;

    public List<GetAnniversaryCategoryResponse> getAnniversaryCategoryResponseList() {
        return anniversaryCategoryRepository.findAll().stream()
                .map(GetAnniversaryCategoryResponse::from)
                .toList();
    }
}
