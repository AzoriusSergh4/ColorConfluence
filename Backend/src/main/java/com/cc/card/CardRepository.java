package com.cc.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.entity.CardCC;
import com.cc.entity.CardTranslation;

public interface CardRepository extends JpaRepository<CardCC,Long>{
	
}
