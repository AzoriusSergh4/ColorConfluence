package com.cc.web.entity;

import javax.persistence.*;

@Entity
public class CardPopularity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private CardCC card;

    private long visited;

    public CardPopularity(){}

    public CardPopularity(CardCC card) {
        this.card = card;
        this.visited = 1;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CardCC getCard() {
        return card;
    }

    public void setCard(CardCC card) {
        this.card = card;
    }

    public long getVisited() {
        return visited;
    }

    public void setVisited(long visited) {
        this.visited = visited;
    }
}
