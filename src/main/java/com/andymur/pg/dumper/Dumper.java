package com.andymur.pg.dumper;

import java.io.IOException;

/**
 * Created by muraand.
 */
public interface Dumper {
    void dump(String fileName) throws IOException;
}
