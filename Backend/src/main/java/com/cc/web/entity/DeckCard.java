package com.cc.web.entity;

import javax.persistence.*;

@Entity
public class DeckCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public DeckCard() {
    }

    public DeckCard(CardCC card, int quantity) {
        this.card = card;
        this.quantity = quantity;
    }

    @OneToOne
    private CardCC card;

    private int quantity;

    public long getId() {
        return id;
    }

    public CardCC getCard() {
        return card;
    }

    public void setCard(CardCC card) {
        this.card = card;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
