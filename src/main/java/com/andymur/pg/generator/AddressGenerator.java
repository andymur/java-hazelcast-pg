package com.andymur.pg.generator;

import com.andymur.pg.domain.Address;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@Component
public class AddressGenerator implements Generator<Address> {
    @Override
    public Address generate() {
        Address address = new Address();
        address.setCountry(RandomStringUtils.randomAlphabetic(5));
        address.setCity(RandomStringUtils.randomAlphabetic(7));
        address.setStreet(RandomStringUtils.randomAlphabetic(5));
        address.setBuilding(Integer.valueOf(RandomUtils.nextInt(1, 100)).toString());

        return address;
    }
}
