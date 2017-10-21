package com.jcoinche.model;

public class Card {

    public enum TypeCard {HEART, SPADES, DIAMOND, CLUBS}

    private TypeCard type;
    private int value;
    private int playerId;

    public Card() {
    }

    public Card (TypeCard type, int value, int playerId) {
        this.type = type;
        this.value = value;
        this.playerId = playerId;
    }

    public TypeCard getType() {
        return type;
    }

    public void setType(TypeCard type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
