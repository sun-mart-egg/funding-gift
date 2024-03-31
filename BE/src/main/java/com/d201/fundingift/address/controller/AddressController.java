package com.d201.fundingift.address.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.address.dto.request.PostAddressRequest;
import com.d201.fundingift.address.dto.response.GetAddressResponse;
import com.d201.fundingift.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.d201.fundingift._common.response.SuccessType.*;

@Tag(name = "addresses", description = "주소 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "주소 추가", description = "새로운 주소를 추가합니다. `Token`")
    @PostMapping
    public SuccessResponse<Void> createAddress(@Valid @RequestBody PostAddressRequest request) {
        addressService.addAddress(request);
        return ResponseUtils.ok(CREATE_ADDRESS_SUCCESS);
    }

    @Operation(summary = "주소 목록 조회", description = "등록된 주소 목록을 조회합니다. `Token`")
    @GetMapping
    public SuccessResponse<List<GetAddressResponse>> getAddresses() {
        List<GetAddressResponse> addresses = addressService.getAddresses();
        return ResponseUtils.ok(addresses, GET_ADDRESSES_SUCCESS);
    }

    @Operation(summary = "주소 수정", description = "특정 주소의 정보를 수정합니다. `Token`")
    @PutMapping("/{id}")
    public SuccessResponse<Void> updateAddress(@PathVariable Long id, @Valid @RequestBody PostAddressRequest addressDto) {
        addressService.updateAddress(id, addressDto);
        return ResponseUtils.ok(UPDATE_ADDRESS_SUCCESS);
    }

    @Operation(summary = "주소 삭제", description = "특정 주소를 삭제합니다. `Token`")
    @DeleteMapping("/{id}")
    public SuccessResponse<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseUtils.ok(DELETE_ADDRESS_SUCCESS);
    }
}
