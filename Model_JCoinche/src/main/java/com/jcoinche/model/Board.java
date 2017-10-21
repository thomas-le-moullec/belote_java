package com.jcoinche.model;

import java.util.List;

public class Board {
    private int turnToPlay;
    private List<Card> fold;
    private Card asset;

    public Board() {
    }

    public Board(int turnToPlay, List<Card> fold, Card asset) {
        this.turnToPlay = turnToPlay;
        this.fold = fold;
        this.asset = asset;
    }

    public int getTurnToPlay() {
        return turnToPlay;
    }

    public void setTurnToPlay(int turnToPlay) {
        this.turnToPlay = turnToPlay;
    }

    public List<Card> getFold() {
        return fold;
    }

    public void setFold(List<Card> fold) {
        this.fold = fold;
    }

    public Card getAsset() {
        return asset;
    }

    public void setAsset(Card asset) {
        this.asset = asset;
    }
}
