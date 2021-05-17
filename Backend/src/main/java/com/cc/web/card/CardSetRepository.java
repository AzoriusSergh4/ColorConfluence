package com.cc.web.card;

import com.cc.web.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {
    List<CardSet> findByCardSetAndCardNumber(String cardSet, String cardNumber);
}
