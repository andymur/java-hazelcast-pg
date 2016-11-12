package com.andymur.pg.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by muraand.
 */
@Component
public class UUIDGenerator implements Generator<String> {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
