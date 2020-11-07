package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardTranslationRepository extends JpaRepository<CardTranslation, Long>{

	List<CardTranslation> findByNameContaining(String name);
	List<CardTranslation> findByCard_Name(String name);

}
