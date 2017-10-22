package com.jcoinche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<String, Integer> valueCard = new HashMap<String, Integer>();
    private Map<String, Integer> valueCardAsset = new HashMap<String, Integer>();
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
        valueCard.put("7", 0);
        valueCard.put("8", 0);
        valueCard.put("9", 0);
        valueCard.put("V", 2);
        valueCard.put("Q", 3);
        valueCard.put("K", 4);
        valueCard.put("10", 10);
        valueCard.put("A", 11);
        valueCardAsset.put("7", 0);
        valueCardAsset.put("8", 0);
        valueCardAsset.put("Q", 3);
        valueCardAsset.put("K", 4);
        valueCardAsset.put("10", 10);
        valueCardAsset.put("A", 11);
        valueCardAsset.put("9", 14);
        valueCardAsset.put("V", 20);

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

    public void setValueCard(Map<String, Integer> valueCard) {
        this.valueCard = valueCard;
    }

    public void setValueCardAsset(Map<String, Integer> valueCardAsset) {
        this.valueCardAsset = valueCardAsset;
    }

    public Map<String, Integer> getValueCard() {
        return valueCard;
    }

    public Map<String, Integer> getValueCardAsset() {
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
