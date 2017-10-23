package com.jcoinche.model;

public class PutCard {
    private boolean verification;

    public PutCard() {

    }

    public PutCard(boolean verification) {
        this.verification = verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    public boolean isVerification() {
        return verification;
    }
}
