package com.chest.model;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class Card {
    private int cardId;
    private int cardNumber;

    public Card() {
    }

    public Card(int cardId, int cardNumber) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
    }

    public Card(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}
