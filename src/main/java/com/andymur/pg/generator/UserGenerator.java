package com.andymur.pg.generator;

import com.andymur.pg.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@Component
public class UserGenerator implements Generator<User> {
    @Autowired
    AddressGenerator addressGenerator;

    @Override
    public User generate() {
        User user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(5));
        user.setLastName(RandomStringUtils.randomAlphabetic(5));
        user.setAddress(addressGenerator.generate());
        return user;
    }
}
