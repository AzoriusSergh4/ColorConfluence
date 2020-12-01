package com.cc.web.card;

import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/card")
public class CardController {
	
	@Autowired
	private CardService cardService;

	@GetMapping("/find/name")
	public List<CardTranslationProjection> getCardByName(@RequestParam String name){
		return cardService.getBasicCardsByTranslationName(name);
	}
	
	
}
