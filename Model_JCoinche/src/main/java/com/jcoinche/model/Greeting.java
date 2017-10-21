package com.jcoinche.model;

public class Greeting {

    private String content;
    private int idPlayer;
    private String room;

    public Greeting() {
    }

    public Greeting(String content, int idPlayer, String Room) {

        this.content = content;
        this.idPlayer = idPlayer;
        this.room = Room;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}

