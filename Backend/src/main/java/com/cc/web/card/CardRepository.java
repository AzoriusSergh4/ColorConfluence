package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.web.entity.CardCC;

import java.util.List;

public interface CardRepository extends JpaRepository<CardCC,Long>{

	boolean existsByName(String name);
	CardCC findByName(String Name);
}
