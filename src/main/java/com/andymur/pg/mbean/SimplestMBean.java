package com.andymur.pg.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@Component
@ManagedResource(description = "Simplest MBean", objectName="App:name=simplestMBean")
public class SimplestMBean {
    private String property;

    @ManagedAttribute(description="The Attribute")
    public String getProperty() {
        return property;
    }

    @ManagedAttribute(description="The Attribute")
    public void setProperty(String property) {
        this.property = property;
    }
}
