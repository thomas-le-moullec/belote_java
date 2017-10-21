package com.jcoinche.model;

public class HelloMessage {
    private String name;
    private int idGuest;

    public HelloMessage() {
    }

    public HelloMessage(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
