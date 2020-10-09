package com.cc.web.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cc.web.entity.CardTranslation;


@RestController
@RequestMapping("/api/card")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@GetMapping("/find/name")
	public List<CardTranslation> getCardByName(@RequestParam String name){
		return cardService.getByName(name);
	}
	
	
}
