package com.andymur.pg.generator;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@Component
public class NumGenerator implements Generator<Number> {

    private int lowerBound = 1;
    private int upperBound = 1001;

    @Override
    public Number generate() {
        return RandomUtils.nextInt(lowerBound, upperBound);
    }
}
