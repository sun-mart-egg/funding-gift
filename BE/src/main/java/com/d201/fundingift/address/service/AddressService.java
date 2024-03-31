package com.d201.fundingift.address.service;

import com.d201.fundingift._common.util.SecurityUtil;
import com.d201.fundingift.address.dto.request.PostAddressRequest;
import com.d201.fundingift.address.dto.response.GetAddressResponse;
import com.d201.fundingift.address.entity.Address;
import com.d201.fundingift.address.repository.AddressRepository;
import com.d201.fundingift.consumer.entity.Consumer;
import com.d201.fundingift.consumer.repository.ConsumerRepository;
import com.d201.fundingift._common.exception.CustomException;
import com.d201.fundingift._common.response.ErrorType;
import com.d201.fundingift.funding.dto.response.GetFundingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.d201.fundingift._common.response.ErrorType.ADDRESS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ConsumerRepository consumerRepository;
    private final SecurityUtil securityUtil;
    @Transactional
    public void addAddress(PostAddressRequest request) {
        Long myConsumerId = securityUtil.getConsumerId();
        Consumer consumer = consumerRepository.findById(myConsumerId)
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        Address address = Address.from(request, consumer);
        addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public List<GetAddressResponse> getAddresses() {
        Long myConsumerId = securityUtil.getConsumerId();
        List<Address> addresses = addressRepository.findByConsumerId(myConsumerId);
        List<GetAddressResponse> getAddressResponseList = addresses.stream()
                .map(GetAddressResponse::from)
                .collect(Collectors.toList());

        return getAddressResponseList;
    }

    @Transactional
    public void updateAddress(Long id, PostAddressRequest addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

        address.updateFrom(addressDto);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

        addressRepository.delete(address);
    }
}
