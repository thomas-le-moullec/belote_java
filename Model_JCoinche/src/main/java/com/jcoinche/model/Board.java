package com.jcoinche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Integer, String> valueCard = new HashMap<Integer, String>();
    private Map<Integer, String> valueCardAsset = new HashMap<Integer, String>();

    public Map<Integer, String> getValueCard() {
        return valueCard;
    }

    public Map<Integer, String> getValueCardAsset() {
        return valueCardAsset;
    }
    private int turnToPlay;
    private List<Card> fold;
    private Card asset;
    private List<Card> pick;

    public Board() {
        turnToPlay = 0;
        valueCard.put(0, "7");
        valueCard.put(0, "8");
        valueCard.put(0, "9");
        valueCard.put(2, "V");
        valueCard.put(3, "Q");
        valueCard.put(4, "K");
        valueCard.put(10, "10");
        valueCard.put(11, "A");
        valueCardAsset.put(0, "7");
        valueCardAsset.put(0, "8");
        valueCardAsset.put(3, "Q");
        valueCardAsset.put(4, "K");
        valueCardAsset.put(10, "10");
        valueCardAsset.put(11, "A");
        valueCardAsset.put(14, "9");
        valueCardAsset.put(20, "V");
        fold = new ArrayList();

        for (int i = 0; i < 32 ; i++) {
            //pick.add(new Card(i % 8, getValueCard().get(i % 8),0));
        }
    }

    public Board(int turnToPlay, List<Card> fold, Card asset, List<Card> pick) {
        this.turnToPlay = turnToPlay;
        this.fold = fold;
        this.asset = asset;
        this.pick = pick;
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

    public List<Card> getPick() {
        return pick;
    }

    public void setPick(List<Card> pick) {
        this.pick = pick;
    }
}
