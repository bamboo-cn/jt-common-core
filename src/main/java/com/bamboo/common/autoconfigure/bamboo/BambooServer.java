package com.bamboo.common.autoconfigure.bamboo;

public class BambooServer {
    private String name;

    public String sayServerName(){
        return "I'm " + name + "! ";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
