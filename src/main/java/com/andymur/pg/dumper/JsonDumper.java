package com.andymur.pg.dumper;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by muraand.
 */
@Component
public class JsonDumper extends BaseDumper {

    private String jsonData;

    public JsonDumper(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void dump(String fileName) throws IOException {
        storeIntoFile(fileName, jsonData);
    }
}
