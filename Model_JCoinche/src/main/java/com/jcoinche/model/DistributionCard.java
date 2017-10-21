package com.jcoinche.model;

import java.util.List;

public class DistributionCard {
    private int idPlayer;
    private List<Card> cards;

    public DistributionCard() {
    }

    public DistributionCard(int idPlayer, List<Card> cards) {
        this.idPlayer = idPlayer;
        this.cards = cards;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer() {
        this.idPlayer = idPlayer;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
