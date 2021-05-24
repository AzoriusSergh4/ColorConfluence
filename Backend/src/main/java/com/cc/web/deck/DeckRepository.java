package com.cc.web.deck;

import com.cc.web.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long>, JpaSpecificationExecutor<Deck> , JpaSpecificationExecutorWithProjection<Deck> {

    Optional<Deck> getByName(String name);

    List<Deck> findByUser_Id(long id);
}
