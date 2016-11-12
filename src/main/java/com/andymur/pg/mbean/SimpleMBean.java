package com.andymur.pg.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Created by muraand.
 */
@ManagedResource(description = "Simple MBean", objectName="App:name=simpleMBean")
public class SimpleMBean implements SimpleMBeanMXBean {
    String value;

    @ManagedAttribute(description="The Attribute")
    public String getValue() {
        return value;
    }

    @ManagedAttribute(description="The Attribute")
    public void setValue(String value) {
        this.value = value;
    }
}
