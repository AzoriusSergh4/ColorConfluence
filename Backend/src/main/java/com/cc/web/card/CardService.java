package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
