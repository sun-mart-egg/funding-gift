package com.d201.fundingift.payment.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.payment.dto.request.PostPaymentInfoRequest;
import com.d201.fundingift.payment.service.PaymentInfoService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
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

    @PostMapping
    public SuccessResponse<IamportResponse<Payment>> postPaymentInfo(@RequestBody @Valid PostPaymentInfoRequest postPaymentInfoRequest) {

        return ResponseUtils.ok(paymentInfoService.postPaymentInfo(postPaymentInfoRequest), SuccessType.POST_PAYMENT_INFO_SUCCESS);
    }
}
