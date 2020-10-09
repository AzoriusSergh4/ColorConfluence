package com.cc.web.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.web.entity.CardTranslation;

@Service
public class CardService {
	
	@Autowired
	private CardTranslationRepository translationRepository;
	
//	public ResponseEntity<List<CardTranslation>> getByName(String name) {
//		List<CardTranslation> cards =  translationRepository.findByNameContaining(name);
//		
//		ResponseEntity<List<CardTranslation>> response = (cards.size() > 0) ? new ResponseEntity<List<CardTranslation>>(cards, HttpStatus.OK) : new ResponseEntity<List<CardTranslation>>(HttpStatus.NO_CONTENT);
//		return response;
//	}
	
	public List<CardTranslation> getByName(String name) {
		return translationRepository.findByNameContaining(name);
	}
}
