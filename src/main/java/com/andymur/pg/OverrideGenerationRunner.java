package com.andymur.pg;

import com.andymur.pg.domain.AddressBookKey;
import com.andymur.pg.domain.AddressBookValue;
import com.andymur.pg.domain.User;
import com.andymur.pg.domain.UserKey;
import com.andymur.pg.dumper.JsonDumper;
import com.andymur.pg.generator.json.OverrideGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muraand.
 */
@Component
public class OverrideGenerationRunner {

    @Autowired
    OverrideGenerator overrideGenerator;
    /*
     [
        {
            "when" : {
                "left" : {
                    "fieldName" : "SHORT_CODE",
                    "comparison" : "EQ",
                    "value" : "PIMC6891_test_999"
                },
            "comparison" : "AND",
                "right" : {
                    "fieldName" : "SOURCE_SYSTEM_NAME",
                    "comparison" : "EQ",
                    "value" : "SUMMIT"
                  }
                },
            "then" : [
                {
                  "fieldName" : "ORG_ID",
                  "comparison" : "EQ",
                  "value" : 5150144
                }
            ]
          }
     ]
     */

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GeneratorConfiguration.class);
        OverrideGenerationRunner runner = applicationContext.getBean(OverrideGenerationRunner.class);
        JsonDumper jsonDumper = new JsonDumper(runner.overrideGenerator.generate(10000));
        jsonDumper.dump("overrides.txt");
    }

    @Configuration
    @ComponentScan(basePackages = {"com.andymur.pg.generator", "com.andymur.pg.generator.json"})
    public static class GeneratorConfiguration {

        @Bean
        public OverrideGenerationRunner overrideGenerationRunner() {
            return new OverrideGenerationRunner();
        }

        @Bean
        public OverrideGenerator overrideGenerator() {
            return new OverrideGenerator();
        }
    }
}
