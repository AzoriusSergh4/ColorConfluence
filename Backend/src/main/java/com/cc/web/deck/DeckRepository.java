package com.cc.web.deck;

import com.cc.web.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

public interface DeckRepository extends JpaRepository<Deck, Long>, JpaSpecificationExecutor<Deck>, JpaSpecificationExecutorWithProjection<Deck> {

}
