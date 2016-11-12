package com.andymur.pg.dumper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by muraand.
 */
@Component
public class PartitionDumper extends BaseDumper {
    // partition id -> count of entries map
    private Map<Integer, Integer> partitionIdCountMap;

    public void setPartitionIdCountMap(Map<Integer, Integer> partitionIdCountMap) {
        this.partitionIdCountMap = partitionIdCountMap;
    }

    @Override
    public void dump(String fileName) {
        StringBuilder sb = new StringBuilder();

        partitionIdCountMap.forEach((k, v) -> sb.append(String.format("%d;%d%n", k, v)));

        try {
            storeIntoFile(fileName, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
