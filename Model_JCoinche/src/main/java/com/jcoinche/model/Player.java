package com.jcoinche.model;

import com.jcoinche.model.Card;

import java.util.List;

public class Player {

    private String id;
    private List<Card> cards;
    private int score;
    private int team;

    public Player() {

    }

    public Player (String id, List<Card> cards, int score, int team) {
        this.id = id;
        this.cards = cards;
        this.score = score;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
