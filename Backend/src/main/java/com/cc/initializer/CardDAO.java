package com.cc.initializer;

import java.sql.PreparedStatement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jayway.jsonpath.internal.function.text.Length;

import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.ForeignData;
import io.magicthegathering.javasdk.resource.Legality;

@Repository
public class CardDAO {
	
	public static final Logger logger = LogManager.getLogger();
	private final JdbcTemplate jdbcTemplate;
	 
    public CardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void storeCards(List<Card> cards) {
    	String query = "INSERT INTO colorconfluence.card (cmc, mana_cost, stat_power, stat_toughness, card_set, card_type,"
    			+ " card_number, legal, rarity, name) VALUES (?,?,?,?,?,?,?,?,?,?)";
    	
    	logger.info("Inserción de información base");
    	
    	jdbcTemplate.batchUpdate(query, cards, cards.size(), 
    			(PreparedStatement ps, Card card) -> {
    				Legality[] legalities = card.getLegalities();
    				boolean legal = false;
    				for(int i = 0; i < legalities.length && !legal; i++) {
    					if(legalities[i].getFormat().equals("Standard") && legalities[i].getLegality().equals("Legal")) {
    						legal = true;
    					}
    				}
    				
    				ps.setInt(1, (int) card.getCmc());
    				ps.setString(2, card.getManaCost());
    				ps.setString(3, card.getPower());
    				ps.setString(4, card.getToughness());
    				ps.setString(5, card.getSet());
    				ps.setString(6, card.getType());
    				ps.setString(7, card.getNumber());
    				ps.setBoolean(8, legal);
    				ps.setString(9, card.getRarity());
    				ps.setString(10, card.getName());
    			}
    	);
    	storeTranslations(cards);
    }
    
    public List<Card> getCard(String name, String number) {
    	return jdbcTemplate.query("SELECT * FROM colorconfluence.card WHERE name LIKE ? AND card_number LIKE ?", new Object[] {name, number}, new CardRowMapper());
    }
    
    public void storeTranslations(List<Card>cards) {
    	String query = "INSERT INTO colorconfluence.card_translation (name, description, lore, url, card_id, lang)"
    			+ "VALUES (?,?,?,?,?,?)";
    	
    	logger.info("Inserción de traducción al inglés");
    	
    	//English Translate
    	jdbcTemplate.batchUpdate(query, cards, cards.size(), 
    			(PreparedStatement ps, Card card) -> {
    				ps.setString(1, card.getName());
    				ps.setString(2, card.getOriginalText());
    				ps.setString(3, card.getFlavor());
    				ps.setString(4, card.getImageUrl());
    				ps.setLong(5,  Long.parseLong(getCard(card.getName(), card.getNumber()).get(0).getId()));
    				ps.setString(6, "English");
    			}
    	);
    	
    	logger.info("Inserción de traducción al español");
    	
    	//Spanish Translate
    	jdbcTemplate.batchUpdate(query, cards, cards.size(), 
    			(PreparedStatement ps, Card card) -> {
    				ForeignData[] fdArray = card.getForeignNames();
    				ForeignData f = new ForeignData();
    				if (fdArray.length > 0) {
    					boolean sp = false;
    					for(int i= 0; i < fdArray.length && !sp; i++) {
    						if(fdArray[i].getLanguage().equals("Spanish")) {
    							f = fdArray[i];
    							sp = true;
    						}
    					}
    					if(!sp) {
    						f = setForeign(card, "Spanish");
    					}
    				}else {
    					f = setForeign(card, "Spanish");
    				}
    				ps.setString(1, f.getName());
    				ps.setString(2, f.getText());
    				ps.setString(3, f.getFlavor());
    				ps.setString(4, f.getImageUrl());
    				ps.setLong(5,  Long.parseLong(getCard(card.getName(), card.getNumber()).get(0).getId()));
    				ps.setString(6, f.getLanguage());
    			}
    	);
    }
    
    private ForeignData setForeign(Card card, String lang) {
    	ForeignData foreignData = new ForeignData();
    	foreignData.setFlavor(card.getFlavor());
    	foreignData.setName(card.getName());
    	foreignData.setImageUrl(card.getImageUrl());
    	foreignData.setLanguage(lang);
    	foreignData.setText(card.getText());
		return foreignData;
    }
    
    public void resetCards() {
    	jdbcTemplate.update("DELETE from colorconfluence.card_translation");
    	jdbcTemplate.update("DELETE from colorconfluence.card");
    	jdbcTemplate.update("alter table colorconfluence.card auto_increment = 1");
    	jdbcTemplate.update("alter table colorconfluence.card_translation auto_increment = 1");   	
    }
}
