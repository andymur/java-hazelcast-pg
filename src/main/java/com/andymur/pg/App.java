package com.andymur.pg;

import com.andymur.pg.generator.AlphaGenerator;
import com.andymur.pg.mbean.SimpleMBean;
import com.andymur.pg.mbean.SimpleMBeanMXBean;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
@Component
public class App {

    public static void main( String[] args ) throws Exception {
        //defaultStart();
        //startFromSpring();
        //startJmx();
        //startJmxFromSpring();
    }

    private static void startJmx() throws Exception {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("App:name=simpleMBean");
        SimpleMBeanMXBean simpleMBeanMXBean = new SimpleMBean();
        mbeanServer.registerMBean(simpleMBeanMXBean, objectName);
        while (true);
    }

    private static void startJmxFromSpring() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hazelcast-jmx-example.xml");
        while (true);
    }

    private static void defaultStart() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        ConcurrentMap<Long, Long> loggedOnUsers = instance.getMap("Users");
        System.out.println(loggedOnUsers);
    }

    private static void startFromConfiguration(String classPathConfiguration) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(new ClasspathXmlConfig(classPathConfiguration));
        ConcurrentMap<Long, Long> loggedOnUsers = instance.getMap("Users");
        System.out.println(loggedOnUsers);
    }

    private static void startFromSpring() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hazelcast-spring.xml");
        App app = context.getBean(App.class);
    }
}
