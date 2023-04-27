package com.doctory.domain.mapper;

import com.doctory.infra.entity.Address;
import com.doctory.infra.entity.Common;
import com.doctory.infra.entity.Person;
import com.doctory.web.request.AddressRequest;
import com.doctory.web.request.PersonRequest;
import org.springframework.stereotype.Component;

import static com.doctory.common.Util.*;

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
    public  Common commonData() {
        var localDateTime = currentDate();
        var common = new Common();
        common.setCreatedBy(1L);
        common.setModifiedBy(1L);
        common.setCreatedDate(localDateTime);
        common.setModifiedDate(localDateTime);
        return common;
    }

    public void updatePersonEntity(PersonRequest personRequest,AddressRequest addressRequest, Person person) {


        var address = person.getAddress();
        updateAddressEntity(addressRequest,address);

        person.setFirstName(personRequest.firstName());
        person.setLastName(personRequest.lastName());
        person.setSurname(personRequest.surname());
        person.setMobileNo(personRequest.mobileNo());
        person.setEmail(personRequest.email());
        person.setMobileNo(personRequest.mobileNo());
        person.setAlternateNo(personRequest.alternateNo());
        person.setAddress(address);

    }
}
