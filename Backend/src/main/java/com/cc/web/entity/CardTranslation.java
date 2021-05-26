package com.cc.web.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CardTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 1000)
    private String lore;

    private String lang;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardCC card;

    @OneToMany(mappedBy = "cardTranslation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("url DESC")
    private List<CardSet> cardSet;

    public CardTranslation() {

    }

    public CardTranslation(String name, String description, String lore, String lang) {
        this.name = name;
        this.description = description;
        this.lore = lore;
        this.lang = lang;
        this.cardSet = new ArrayList<>();
    }

    public CardTranslation(String name, String description, String lore, String lang, CardSet set) {
        this.name = name;
        this.description = description;
        this.lore = lore;
        this.lang = lang;
        this.cardSet = new ArrayList<>();
        this.cardSet.add(set);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public CardCC getCard() {
        return card;
    }

    public void setCard(CardCC card) {
        this.card = card;
    }

    public List<CardSet> getCardSet() {
        return cardSet;
    }

    public void setCardSet(List<CardSet> splashArt) {
        this.cardSet = splashArt;
    }

}
