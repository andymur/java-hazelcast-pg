package com.andymur.pg.generator.json;

import com.andymur.pg.generator.AlphaGenerator;
import com.andymur.pg.generator.NumGenerator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muraand.
 */
@Component
public class OverrideGenerator {

    @Autowired
    private AlphaGenerator alphaGenerator;

    @Autowired
    private NumGenerator numGenerator;

    public String generate(Integer numberOfOverrides) throws JsonProcessingException {
        List<OverrideStatement> overrideStatements = new ArrayList<>(numberOfOverrides);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        for (int i = 0; i < numberOfOverrides; i++) {
            overrideStatements.add(generateOverride());
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(overrideStatements);
    }

    private OverrideStatement generateOverride() {

        WhenStatement when = new WhenStatement(
                new LeftRightStatement("SHORT_CODE", generateShortCode()),
                new LeftRightStatement("SOURCE_SYSTEM_NAME", "SUMMIT")
        );
        return new OverrideStatement(when, numGenerator.generate().intValue());
    }

    private String generateShortCode() {
        return alphaGenerator.generate().concat("_test_").concat(numGenerator.generate().toString());
    }

    private static class OverrideStatement {
        private WhenStatement when;
        private List<Assignment> then;

        OverrideStatement(WhenStatement when, Integer value) {
            this.when = when;
            then = new ArrayList<>();
            Assignment assignment = new Assignment();
            assignment.setValue(value);
            then.add(assignment);
        }
    }

    private static class WhenStatement {
        LeftRightStatement left;
        String comparison = "AND";
        LeftRightStatement right;

        public WhenStatement() {
        }

        WhenStatement(LeftRightStatement left, LeftRightStatement right) {
            this.left = left;
            this.right = right;
        }
    }

    private static class LeftRightStatement {
        private String fieldName;
        private String comparison = "EQ";
        private String value;

        LeftRightStatement(String fieldName, String value) {
            this.fieldName = fieldName;
            this.value = value;
        }
    }

    private static class Assignment {
        private String fieldName = "ORG_ID";
        private String comparison = "EQ";
        private Integer value;

        void setValue(Integer value) {
            this.value = value;
        }
    }
}
