package com.chest.model;

/**
 * Created by TrungTV on 01/03/2018.
 */

public class Card {
    private int cardId;
    private int cardNumber;
    private String cardName;
    private int cardIndex;

    public Card() {
    }

    public Card(int cardId, int cardNumber, String cardName, int cardIndex) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardIndex = cardIndex;
    }

    public Card(int cardNumber, String cardName, int cardIndex) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardIndex = cardIndex;
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
