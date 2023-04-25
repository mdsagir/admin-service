package com.doctory.domain.mapper;

import com.doctory.infra.entity.Address;
import com.doctory.web.request.AddressRequest;
import org.springframework.stereotype.Component;

@Component
public class CommonMapper {

    public Address toAddressEntity(AddressRequest addressRequest) {
        return new Address(addressRequest.addressLine1(), addressRequest.addressLine2(), addressRequest.state(),
                addressRequest.country(), addressRequest.pinCode());
    }

    public void updateAddressEntity(AddressRequest addressRequest, Address address) {
        address.setAddressLine1(addressRequest.addressLine1());
        address.setAddressLine2(addressRequest.addressLine2());
        address.setPinCode(addressRequest.pinCode());
        address.setState(addressRequest.state());
        address.setCountry(addressRequest.country());

    }
}
