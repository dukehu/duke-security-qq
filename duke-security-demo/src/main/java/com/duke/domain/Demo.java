package com.duke.domain;

/**
 * Created duke on 2017/12/30
 */
public class Demo {
    private String id;

    private String name;

    public Demo() {
    }

    public Demo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
