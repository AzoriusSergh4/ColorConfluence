package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/card")
public class CardController {
	
	@Autowired
	private CardService cardService;

	@GetMapping("/find/name")
	public Page<CardTranslationProjection> getCardByName(@RequestParam String name){
		return cardService.getBasicCardsByTranslationName(name);
	}

	@GetMapping("/find")
	public Page<CardTranslationProjection> getCardsByCriteria(@RequestParam Map<String,String> criteria){
		return cardService.getBasicCardsByCriteria(criteria);
	}

	@GetMapping("/{id}")
	public CardTranslation getCardById(@PathVariable long id){
		return cardService.getCardById(id);
	}
	
	
}
