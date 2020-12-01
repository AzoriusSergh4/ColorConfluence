package com.cc.web.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.magicthegathering.javasdk.resource.Legality;

import javax.persistence.*;

@Entity
public class CardLegality {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String format;
    private String legality;

    @JsonIgnore
    @ManyToOne
    private CardCC card;

    public CardLegality(){}

    public CardLegality(Legality l){
        this.format = l.getFormat();
        this.legality = l.getLegality();

    }

    public long getId() {
        return id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLegality() {
        return legality;
    }

    public void setLegality(String legality) {
        this.legality = legality;
    }

    public CardCC getCard() {
        return card;
    }

    public void setCard(CardCC card) {
        this.card = card;
    }
}
