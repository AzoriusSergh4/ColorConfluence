package com.cc.web.card;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardPopularity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPopularityRepository extends JpaRepository<CardPopularity, Long> {

    CardPopularity findByCard(CardCC cardCC);
}
