package com.cc.web.card;

import com.cc.web.entity.CardLegality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardLegalitiesRepository extends JpaRepository<CardLegality, Long> {
}
