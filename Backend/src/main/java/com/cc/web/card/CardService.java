package com.cc.web.card;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardPopularity;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import com.cc.web.specifications.CardSpecifications;
import com.cc.web.specifications.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CardService {

    private final String LANG_ENGLISH = "English";

    private final CardTranslationRepository translationRepository;

    private final CardRepository cardRepository;

    private final CardPopularityRepository cardPopularityRepository;

    @Autowired
    public CardService(CardTranslationRepository translationRepository, CardRepository cardRepository, CardPopularityRepository cardPopularityRepository) {
        this.translationRepository = translationRepository;
        this.cardRepository = cardRepository;
        this.cardPopularityRepository = cardPopularityRepository;
    }

    /**
     * Count the cards hat matches the given name
     * @param criteria criteria to filter by
     * @return the number of cards that match
     */
    public long countBasicCardsByCriteria(Map<String, String> criteria) {
        criteria.put("page", "0");
        return getBasicCardsByCriteria(criteria).getTotalElements();
    }

    /**
     *
     * @param criteria criteria to filter by
     * @return
     */
    public Page<CardTranslation> getFullCardsByCriteria(Map<String, String> criteria) {
        Specification<CardTranslation> s = setCardTranslationSpecification(criteria);
        return translationRepository.findAll(s, PageRequest.of(Integer.parseInt(criteria.get("page")), Integer.parseInt(criteria.get("pageSize")), Sort.by(Sort.Direction.ASC, "name")));
    }

    /**
     * Gets the cards filtered by criteria
     *
     * @param criteria criteria to filter by
     * @return the page of matched cards
     */
    public Page<CardTranslationProjection> getBasicCardsByCriteria(Map<String, String> criteria) {
        Specification<CardTranslation> s = setCardTranslationSpecification(criteria);
        return translationRepository.findAll(s, CardTranslationProjection.class, PageRequest.of(Integer.parseInt(criteria.get("page")), 60, Sort.by(Sort.Direction.ASC, "name")));
    }

    /**
     * Initialise the cards specifications
     * @param criteria criteria to filter by
     * @return the specification with the criteria
     */
    private Specification<CardTranslation> setCardTranslationSpecification(Map<String, String> criteria) {
        return Specification.where(criteria.get("name") == null ? null : CardSpecifications.nameContains(criteria.get("name")))
                .and(criteria.get("text") == null ? null : CardSpecifications.descriptionContains(criteria.get("text")))
                .and(criteria.get("lore") == null ? null : CardSpecifications.loreContains(criteria.get("lore")))
                .and(criteria.get("lang") == null ? CardSpecifications.langEquals(LANG_ENGLISH) : CardSpecifications.langEquals(criteria.get("lang")))
                .and(criteria.get("type") == null ? null : CardSpecifications.typeContains(criteria.get("type")))
                .and(criteria.get("colors") == null ? null : CardSpecifications.colorContains(criteria.get("colors")))
                .and((criteria.get("manaCost") == null ? null : CardSpecifications.manaCostContains(criteria.get("manaCost"))))
                .and((criteria.get("rarity") == null ? null : CardSpecifications.rarityContains(criteria.get("rarity"))))
                .and((criteria.get("cmc") == null ? null : CardSpecifications.statComparatorContains(criteria.get("cmc"), "cmc")))
                .and((criteria.get("power") == null ? null : CardSpecifications.statComparatorContains(criteria.get("power"), "power")))
                .and((criteria.get("toughness") == null ? null : CardSpecifications.statComparatorContains(criteria.get("toughness"), "toughness")))
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
    }

    /**
     * Return teh card translation identified by its id
     * @param id the id of the card
     * @return the card, or null
     */
    public CardTranslation getCardById(long id) {
        Optional<CardTranslation> opt = translationRepository.findById(id);
        if(opt.isPresent()){
            var pop = cardPopularityRepository.findByCard(opt.get().getCard());
            if (pop == null) {
                pop = new CardPopularity(opt.get().getCard());
            } else {
                pop.setVisited(pop.getVisited() + 1);
            }
            cardPopularityRepository.save(pop);
        }
        return opt.orElse(null);
    }

    /**
     * Return teh card identified by its id
     * @param id the id of the card
     * @return the card, or null
     */
    public CardCC getCardCCById(long id) {
        return cardRepository.findById(id);
    }

    /**
     * Get card popularities
     * @param page the page
     * @return a page with popularities
     */
    public Page<CardPopularity> getCardPopularities(int page) {
        return cardPopularityRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "visited")));
    }
}
