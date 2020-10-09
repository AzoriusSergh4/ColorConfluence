package com.cc.web.card;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.web.entity.CardCC;

public interface CardRepository extends JpaRepository<CardCC,Long>{
	
}
