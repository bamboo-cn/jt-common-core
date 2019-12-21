package com.bamboo.common.autoconfigure.bamboo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bamboo")
public class BambooServerProperties {

    private static final String NAME = "bamboo_server0";

    private String name = NAME;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}