package com.andymur.pg.dumper;

import com.andymur.pg.domain.Address;
import com.andymur.pg.domain.User;
import com.andymur.pg.domain.UserKey;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by muraand.
 */
@Component
public class UserMapDumper extends BaseDumper {
    @Resource(name = "users")
    IMap<UserKey, User> userMap;

    @Override
    public void dump(String fileName) {
        StringBuilder sb = new StringBuilder();

        userMap.forEach((k, v) -> {
                Address address = v.getAddress();
                String addressName = address.getCountry().concat(":").concat(address.getCity()).concat(":").concat(address.getStreet());
                sb.append(String.format("%s -> (%s, %s)%n", k.getUuid(), v, addressName));
            }
        );

        try {
            storeIntoFile(fileName, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
