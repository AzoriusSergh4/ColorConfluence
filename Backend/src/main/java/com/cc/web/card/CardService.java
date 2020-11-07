package com.cc.web.card;

import java.util.List;

import com.cc.web.entity.CardCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.web.entity.CardTranslation;

@Service
public class CardService {
	
	@Autowired
	private CardTranslationRepository translationRepository;

	@Autowired
	private CardRepository cardRepository;
	
	public List<CardTranslation> getByTranslationName(String name) {
		return translationRepository.findByNameContaining(name);
	}
}
