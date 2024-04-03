package com.d201.fundingift.payment.controller;

import com.d201.fundingift._common.response.ErrorResponse;
import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.payment.dto.request.PostPaymentInfoRequest;
import com.d201.fundingift.payment.service.PaymentInfoService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "paymentInfo", description = "결제 정보 관련 API")
@RestController
@RequestMapping("/api/payment-info")
@RequiredArgsConstructor
public class PaymentInfoController {

    private final PaymentInfoService paymentInfoService;

    @Operation(summary = "결제 정보 검증 및 저장",
            description = """
                           `token` \n
                           결제 정보를 검증하고 저장합니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 결제 완료 여부 / 펀딩 참여 금액과 실제 결제 금액 비교",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping
    public SuccessResponse<IamportResponse<Payment>> postPaymentInfo(@RequestBody @Valid PostPaymentInfoRequest postPaymentInfoRequest) {

        return ResponseUtils.ok(paymentInfoService.postPaymentInfo(postPaymentInfoRequest), SuccessType.POST_PAYMENT_INFO_SUCCESS);
    }
}
