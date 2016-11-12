package com.andymur.pg.dumper;

import com.google.common.base.Charsets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by muraand.
 */
abstract public class BaseDumper implements Dumper {
    protected void storeIntoFile(String fileName, String data) throws IOException {
        Files.write(Paths.get(fileName), data.getBytes(Charsets.UTF_8),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    }
}
