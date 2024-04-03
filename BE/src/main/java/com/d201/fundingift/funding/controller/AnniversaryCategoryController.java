package com.d201.fundingift.funding.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.funding.dto.response.GetAnniversaryCategoryResponse;
import com.d201.fundingift.funding.service.AnniversaryCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "anniversaryCategory", description = "기념일 카테고리 관련 API")
@RestController
@RequestMapping("api/anniversary-category")
@RequiredArgsConstructor
public class AnniversaryCategoryController {

    private final AnniversaryCategoryService anniversaryCategoryService;

    @Operation(summary = "기념일 카테고리 전체 조회",
            description = "기념일 카테고리를 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true)
    })
    @GetMapping
    public SuccessResponse<List<GetAnniversaryCategoryResponse>> getAnniversaryCategoryResponseList() {

        return ResponseUtils.ok(anniversaryCategoryService.getAnniversaryCategoryResponseList(), SuccessType.GET_ANNIVERSARY_CATEGORIES_SUCCESS);
    }
}
