package com.jcoinche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Integer, String> valueCard = new HashMap<Integer, String>();
    private Map<Integer, String> valueCardAsset = new HashMap<Integer, String>();
    private Map<Integer, String> orderCard = new HashMap<Integer, String>();
    private Map<Integer, Card.TypeCard> typeCard = new HashMap<Integer, Card.TypeCard>();

    private int turnToPlay;
    private List<Card> fold;
    private Card asset;
    private List<Card> pick;

    public Board() {
        pick = new ArrayList<Card>();
        fold = new ArrayList<Card>();
        turnToPlay = 0;
        orderCard.put(0, "7");
        orderCard.put(1, "8");
        orderCard.put(2, "9");
        orderCard.put(3, "V");
        orderCard.put(4, "Q");
        orderCard.put(5, "K");
        orderCard.put(6, "10");
        orderCard.put(7, "A");
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

        typeCard.put(0, Card.TypeCard.HEART);
        typeCard.put(1, Card.TypeCard.SPADES);
        typeCard.put(2, Card.TypeCard.DIAMOND);
        typeCard.put(3, Card.TypeCard.CLUBS);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println("TypeCard:"+typeCard.get(i)+ "Value :"+getOrderCard().get(j));
                pick.add(new Card(typeCard.get(i), getOrderCard().get(j), 0));
            }
        }
    }

    public Board(int turnToPlay, List<Card> fold, Card asset, List<Card> pick) {
        this.turnToPlay = turnToPlay;
        this.fold = fold;
        this.asset = asset;
        this.pick = pick;
    }

    public Map<Integer, Card.TypeCard> getTypeCard() {
        return typeCard;
    }

    public void setOrderCard(Map<Integer, String> orderCard) {
        this.orderCard = orderCard;
    }

    public Map<Integer, String> getOrderCard() {
        return orderCard;
    }

    public void setTypeCard(Map<Integer, Card.TypeCard> typeCard) {
        this.typeCard = typeCard;
    }

    public void setValueCard(Map<Integer, String> valueCard) {
        this.valueCard = valueCard;
    }

    public void setValueCardAsset(Map<Integer, String> valueCardAsset) {
        this.valueCardAsset = valueCardAsset;
    }

    public Map<Integer, String> getValueCard() {
        return valueCard;
    }

    public Map<Integer, String> getValueCardAsset() {
        return valueCardAsset;
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
