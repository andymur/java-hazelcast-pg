package com.andymur.java.pg;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello hazelcast!
 *
 */
public class HazelcastRunner {
    public static void main( String[] args ) {
        //runHazelcastServer();
        runHazelcastServerFromSpring();
    }

    private static void runHazelcastServer() {
        Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(/*put your configuration here*/);

    }

    private static void runHazelcastServerFromSpring() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("hazelcast-spring.xml");
    }
}
