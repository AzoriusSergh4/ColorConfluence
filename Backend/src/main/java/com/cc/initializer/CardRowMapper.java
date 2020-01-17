package com.cc.initializer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.Legality;

public class CardRowMapper implements RowMapper<Card>{
	@Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
		Legality l = new Legality();
		l.setFormat("Standard");
		if(rs.getBoolean("legal")) {
			l.setLegality("Legal");
		}else {
			l.setLegality("Banned");;
		}
		Legality[] legs = {l};
		
        Card card = new Card();
        card.setId(String.valueOf(rs.getLong("id")));
        card.setCmc(rs.getInt("cmc"));
        card.setManaCost(rs.getString("mana_cost"));
        card.setPower(rs.getString("stat_power"));
        card.setToughness(rs.getString("stat_toughness"));
        card.setSet(rs.getString("card_set"));
        card.setType(rs.getString("card_type"));
        card.setNumber(rs.getString("card_number"));
        card.setLegalities(legs);
        card.setRarity(rs.getString("rarity"));
        card.setName(rs.getString("name"));
        return card;
    }
}
