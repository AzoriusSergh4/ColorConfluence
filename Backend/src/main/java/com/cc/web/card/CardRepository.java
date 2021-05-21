package com.cc.web.card;

import com.cc.web.entity.CardCC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardCC,Long>{

	boolean existsByName(String name);
	CardCC findById(long id);
}
