package com.cc.web.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
public class CardSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cardNumber;
    private String cardSet;
    private String url;

    @JsonIgnore
    @ManyToOne
    private CardTranslation cardTranslation;

    public CardSet(){}

    public CardSet(String cardSet, String cardNumber, String url){
        this.cardSet = cardSet;
        this.cardNumber = cardNumber;
        this.url = url;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String set) {
        this.cardSet = set;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardTranslation getCardTranslation() {
        return cardTranslation;
    }

    public void setCardTranslation(CardTranslation cardTranslation) {
        this.cardTranslation = cardTranslation;
    }
}
