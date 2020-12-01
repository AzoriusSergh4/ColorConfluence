package com.cc.web.entity.projection;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardSet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface CardTranslationProjection {

    long getId();

    String getName();

    @JsonIgnore
    CardCC getCard();

    List<CardSet> getCardSet();

    void setCardSet(List<CardSet> splashArt);
}
