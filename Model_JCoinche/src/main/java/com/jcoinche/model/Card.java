package com.jcoinche.model;

import java.util.*;

public class Card {

    public enum TypeCard {HEART, SPADES, DIAMOND, CLUBS}

    private TypeCard type;
    private String value;
    private int playerId;

    public Card() {
    }

    public Card (TypeCard type, String value, int playerId) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
