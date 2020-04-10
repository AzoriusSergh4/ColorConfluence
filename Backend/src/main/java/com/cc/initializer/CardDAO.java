package com.cc.initializer;

import java.sql.PreparedStatement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cc.card.CardRepository;
import com.cc.card.CardTranslationRepository;
import com.cc.entity.CardCC;
import com.cc.entity.CardTranslation;

import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.ForeignData;
import io.magicthegathering.javasdk.resource.Legality;

@Service
public class CardDAO {
	
	public static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardTranslationRepository translationRepository;
	
    public void storeCards(List<Card> cards) {
    	for (Card card : cards) {
    		CardCC c = new CardCC(card);
    		cardRepository.save(c);
    		ForeignData[] foreignData = card.getForeignNames();
    		CardTranslation english = new CardTranslation(card.getName(), card.getOriginalText(), card.getFlavor(), card.getImageUrl(), "English");
    		english.setCard(c);
    		translationRepository.save(english);
    		CardTranslation spanish = null;
    		for(int i = 0; i < foreignData.length; i++) {
    			if(foreignData[i].getLanguage().equals("Spanish")) {
    				spanish = new CardTranslation(foreignData[i].getName(), foreignData[i].getText(), foreignData[i].getFlavor(), foreignData[i].getImageUrl(), foreignData[i].getLanguage());
    				spanish.setCard(c);
    				translationRepository.save(spanish);
    			}
    		}
    		
    		
    	}
    	
    }
    
  
}
