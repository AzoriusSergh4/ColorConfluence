package com.cc.web.deck;

import com.cc.web.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> getByName(String name);

    List<Deck> findByUser_Id(long id);
}
