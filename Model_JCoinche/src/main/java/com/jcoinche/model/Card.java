package com.jcoinche.model;

public class Card {

    public enum TypeCard {HEART, SPADES, DIAMOND, CLUBS}

    private TypeCard type = TypeCard.SPADES;
    private int value = 7;
    private int playerId = 889;

    public TypeCard getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getPlayerId() {
        return playerId;
    }
}
