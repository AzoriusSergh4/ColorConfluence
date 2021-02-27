package com.cc.web.card;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardSet;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import com.cc.web.specifications.CardSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService {
	
	@Autowired
	private CardTranslationRepository translationRepository;

	/**
	 * Gets the cards filtered by name and remove duplicates
	 * @param name Name of the card
	 * @return the list of amtched cards
	 */
	public Page<CardTranslationProjection> getBasicCardsByTranslationName(String name) {
		Specification<CardTranslation> s = Specification
				.where(name == null ? null : CardSpecifications.nameContains(name));
		return translationRepository.findAll(s, CardTranslationProjection.class, PageRequest.of(0,60));
	}

	public Page<CardTranslationProjection> getBasicCardsByCriteria(Map<String, String> criteria){
		Specification<CardTranslation> s = Specification
				.where(criteria.get("name") == null ? null : CardSpecifications.nameContains(criteria.get("name")))
				.and(criteria.get("text") == null ? null : CardSpecifications.descriptionContains(criteria.get("text")))
				.and(criteria.get("lore") == null ? null : CardSpecifications.loreContains(criteria.get("lore")))
				.and(criteria.get("lang") == null ? CardSpecifications.langEquals("English") : CardSpecifications.langEquals(criteria.get("lang")))
				.and(criteria.get("type") == null ? null : CardSpecifications.typeContains(criteria.get("type")))
				.and(criteria.get("colors") == null ? null : CardSpecifications.colorContains(criteria.get("colors")))
				.and((criteria.get("manaCost") == null ? null : CardSpecifications.manaCostContains(criteria.get("manaCost"))))
				.and((criteria.get("rarity") == null ? null : CardSpecifications.rarityContains(criteria.get("rarity"))))
				.and((criteria.get("cmc") == null ? null : CardSpecifications.statComparatorContains(criteria.get("cmc"),"cmc")))
				.and((criteria.get("power") == null ? null : CardSpecifications.statComparatorContains(criteria.get("power"),"power")))
				.and((criteria.get("toughness") == null ? null : CardSpecifications.statComparatorContains(criteria.get("toughness"),"toughness")))
				.and((criteria.get("set") == null ? null : CardSpecifications.cardSetContains(criteria.get("set"))))
				.and((criteria.get("standard") == null ? null : CardSpecifications.legalityContains("Standard", criteria.get("standard"))))
				.and((criteria.get("duel") == null ? null : CardSpecifications.legalityContains("Duel", criteria.get("duel"))))
				.and((criteria.get("legacy") == null ? null : CardSpecifications.legalityContains("Legacy", criteria.get("legacy"))))
				.and((criteria.get("modern") == null ? null : CardSpecifications.legalityContains("Modern", criteria.get("modern"))))
				.and((criteria.get("vintage") == null ? null : CardSpecifications.legalityContains("Vintage", criteria.get("vintage"))))
				.and((criteria.get("commander") == null ? null : CardSpecifications.legalityContains("Commander", criteria.get("commander"))))
				.and((criteria.get("historic") == null ? null : CardSpecifications.legalityContains("Historic", criteria.get("historic"))))
				.and((criteria.get("pioneer") == null ? null : CardSpecifications.legalityContains("Pioneer", criteria.get("pioneer"))))
				.and((criteria.get("penny") == null ? null : CardSpecifications.legalityContains("Penny", criteria.get("penny"))));

		return translationRepository.findAll(s,CardTranslationProjection.class, PageRequest.of(0,60));
	}

	private List<CardTranslationProjection> filterCards(Page<CardTranslationProjection> cards){
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

	public CardTranslation getCardById(long id) {
		Optional<CardTranslation> opt = translationRepository.findById(id);
		return opt.orElse(null);
	}
}
