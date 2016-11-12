package com.andymur.pg.mbean;

import org.springframework.jmx.export.annotation.ManagedResource;

import javax.management.MXBean;

/**
 * Created by muraand.
 */
public interface SimpleMBeanMXBean {
    String getValue();
    void setValue(String value);
}
