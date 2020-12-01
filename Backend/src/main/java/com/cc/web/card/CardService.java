package com.cc.web.card;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardSet;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CardService {
	
	@Autowired
	private CardTranslationRepository translationRepository;

	@Autowired
	private CardRepository cardRepository;

	/**
	 * Gets the cards filtered by name and remove duplicates
	 * @param name Name of the card
	 * @return the list of amtched cards
	 */
	public List<CardTranslationProjection> getBasicCardsByTranslationName(String name) {
		List<CardTranslationProjection> cards = translationRepository.findByNameContaining(name, CardTranslationProjection.class);
		Set<CardCC> selected = new HashSet<>();
		List<CardTranslationProjection> cardsFiltered = new ArrayList<>();
		//Remove duplicates and select first set with card image
		for(CardTranslationProjection c : cards) {
			if(!selected.contains(c.getCard())){
				selected.add(c.getCard());
				for(CardSet s : c.getCardSet()){
					if(s.getUrl() != null){
						List<CardSet> set = new ArrayList<>();
						set.add(s);
						c.setCardSet(set);
						break;
					}
				}
				cardsFiltered.add(c);
			}
		}
		return cardsFiltered;
	}
}
