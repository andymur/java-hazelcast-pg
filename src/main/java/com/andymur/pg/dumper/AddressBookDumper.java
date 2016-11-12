package com.andymur.pg.dumper;

import com.andymur.pg.domain.AddressBookKey;
import com.andymur.pg.domain.AddressBookValue;
import com.andymur.pg.domain.User;
import com.google.common.base.Charsets;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

/**
 * Created by muraand.
 */
@Component
public class AddressBookDumper extends BaseDumper {
    @Resource(name = "addressBook")
    IMap<AddressBookKey, AddressBookValue> addressBook;

    @Override
    public void dump(String fileName) {
        StringBuilder sb = new StringBuilder();

        addressBook.forEach((k, v) -> {
                final String users = v.getUsers().stream().map(User::toString).collect(Collectors.joining(", "));
                sb.append(
                        String.format("%s:%s:%s -> [%s]%n", k.getCountry(), k.getCity(), k.getStreet(), users)
                );
            }
        );

        try {
            storeIntoFile(fileName, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
