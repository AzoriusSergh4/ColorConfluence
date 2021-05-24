package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import com.cc.web.specifications.CardSpecifications;
import com.cc.web.specifications.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CardService {
	
	@Autowired
	private CardTranslationRepository translationRepository;

	/**
	 * Gets the cards filtered by name
	 * @param name Name of the card
	 * @return the list of matched cards
	 */
	public Page<CardTranslationProjection> getBasicCardsByTranslationName(String name, int pageSize) {
		Specification<CardTranslation> s = Specification
				.where(name == null ? null : CardSpecifications.nameContains(name))
				.and(CardSpecifications.langEquals("English"));
		return translationRepository.findAll(s, CardTranslationProjection.class, PageRequest.of(0,pageSize));
	}

	public Page<CardTranslation> getCardsByTranslationName(String name, int page, int pageSize) {
		Specification<CardTranslation> s = Specification
				.where(name == null ? null : CardSpecifications.nameContains(name))
				.and(CardSpecifications.langEquals("English"));
		return translationRepository.findAll(s, PageRequest.of(page,pageSize));
	}

	public long countBasicCardsByName(String name) {
		return  getBasicCardsByTranslationName(name, Integer.MAX_VALUE).getTotalElements();
	}

	/**
	 * Gets the cards filtered by criteria
	 * @param criteria crite to filter by
	 * @return the page of matched cards
	 */
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
				.and((criteria.get("penny") == null ? null : CardSpecifications.legalityContains("Penny", criteria.get("penny"))))
				.and(CommonSpecification.distinct());

		return translationRepository.findAll(s,CardTranslationProjection.class, PageRequest.of(Integer.parseInt(criteria.get("page")),60));
	}

	public CardTranslation getCardById(long id) {
		Optional<CardTranslation> opt = translationRepository.findById(id);
		return opt.orElse(null);
	}
}
