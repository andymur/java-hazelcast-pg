package com.andymur.pg.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@Component
public class AlphaGenerator implements  Generator<String> {
    private static final int DEFAULT_LENGTH = 5;

    private Case alphaCase = Case.UPPER;
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public void setCase(Case alphaCase) {
        this.alphaCase = alphaCase;
    }

    @Override
    public String generate() {
        String randomAlpha = RandomStringUtils.randomAlphabetic(length != 0 ? length : DEFAULT_LENGTH);

        switch (alphaCase) {
            case UPPER:
                return randomAlpha.toUpperCase();
            case LOWER:
                return randomAlpha.toLowerCase();
            case ANY:
                return randomAlpha;
            default:
                return randomAlpha;
        }
    }

    static public enum Case {
        UPPER, LOWER, ANY
    }
}
