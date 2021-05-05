package com.cc.initializer;

import java.util.ArrayList;
import java.util.List;

import com.cc.web.card.CardLegalitiesRepository;
import com.cc.web.card.CardSetRepository;
import com.cc.web.entity.CardLegality;
import com.cc.web.entity.CardSet;
import io.magicthegathering.javasdk.resource.Legality;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.web.card.CardRepository;
import com.cc.web.card.CardTranslationRepository;
import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardTranslation;

import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.ForeignData;

@Service
public class CardDAO {
	
	public static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardTranslationRepository translationRepository;

	@Autowired
	private CardSetRepository cardSetRepository;

	@Autowired
	private CardLegalitiesRepository cardLegalitiesRepository;
	
    public void storeCards(List<Card> cards) {
    	for (Card card : cards) {
    		if(!cardRepository.existsByName(card.getName())){
				CardCC c = new CardCC(card);
				List<CardLegality> legalities = new ArrayList<>();
				if(card.getLegalities() != null) {
					for (Legality l : card.getLegalities()){
						CardLegality legality = new CardLegality(l);
						legality.setCard(c);
						c.getLegalities().add(legality);
						legalities.add(legality);
					}
				}
				cardRepository.save(c);
				for(CardLegality legality : legalities){
					cardLegalitiesRepository.save(legality);
				}
				CardSet set = new CardSet(card.getSet(), card.getNumber(), card.getImageUrl());
				CardTranslation english = new CardTranslation(card.getName(), card.getOriginalText(), card.getFlavor(), "English", set);
				english.setCard(c);
				translationRepository.save(english);
				set.setCardTranslation(english);
				cardSetRepository.save(set);
				CardTranslation spanish;
				ForeignData[] foreignData = card.getForeignNames();
				if(foreignData != null){
					for (ForeignData foreignDatum : foreignData) {
						if (foreignDatum.getLanguage().equals("Spanish")) {
							set = new CardSet(card.getSet(), card.getNumber(), foreignDatum.getImageUrl());
							spanish = new CardTranslation(foreignDatum.getName(), foreignDatum.getText(), foreignDatum.getFlavor(), foreignDatum.getLanguage(), set);
							spanish.setCard(c);
							translationRepository.save(spanish);
							set.setCardTranslation(spanish);
							cardSetRepository.save(set);
						}
					}
				}
			}else{
    			for(CardTranslation ct : translationRepository.findByCard_Name(card.getName())){
    				if(ct.getLang().equals("English")){
						CardSet set = new CardSet(card.getSet(), card.getNumber(), card.getImageUrl());
						ct.getCardSet().add(set);
						translationRepository.save(ct);
						set.setCardTranslation(ct);
						cardSetRepository.save(set);
					}
    				if(card.getForeignNames() != null){
						for(ForeignData fd : card.getForeignNames()){
							if(fd.getLanguage().equals(ct.getLang())){
								CardSet set = new CardSet(card.getSet(), card.getNumber(), fd.getImageUrl());
								ct.getCardSet().add(set);
								translationRepository.save(ct);
								set.setCardTranslation(ct);
								cardSetRepository.save(set);
							}
						}
					}
				}
			}
    	}
    	
    }
    
  
}
