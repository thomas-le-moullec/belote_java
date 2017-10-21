package com.jcoinche.model;

public class HelloMessage {
    private String name;
    private int idGuest;

    public HelloMessage() {
    }

    public HelloMessage(String name, int id) {

        this.name = name;
        this.idGuest = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getIdGuest() {

        return idGuest;
    }

    public void setName(int id) {

        this.idGuest = id;
    }
}
