package com.cc.web.card;

import java.util.List;

import com.cc.web.entity.CardCC;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.web.entity.CardTranslation;

public interface CardTranslationRepository extends JpaRepository<CardTranslation, Long>{

	List<CardTranslation> findByNameContaining(String name);
	List<CardTranslation> findByCard_Name(String name);

}
