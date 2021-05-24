package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/card")
public class CardController {
	
	@Autowired
	private CardService cardService;

	@GetMapping("/find/name")
	public Page<CardTranslationProjection> getCardByName(@RequestParam String name, int pageSize){
		return cardService.getBasicCardsByTranslationName(name, pageSize);
	}

	@GetMapping("/find/full/name")
	public Page<CardTranslation> getFullCardByName(@RequestParam String name, int page, int pageSize){
		return cardService.getCardsByTranslationName(name, page, pageSize);
	}

	@GetMapping("/count/name")
	public long countByName(@RequestParam String name) {
		return cardService.countBasicCardsByName(name);
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
