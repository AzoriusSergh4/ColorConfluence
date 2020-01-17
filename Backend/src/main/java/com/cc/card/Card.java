package com.cc.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "colorconfluence.card")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "cmc", nullable = true)
	private int cmc;
	
	@Column(name = "mana_cost", nullable = true, length = 100)
	private String manaCost;
	
	@Column(name = "stat_power", nullable = true, length = 10)
	private String power;
	
	@Column(name = "stat_toughness", nullable = true, length = 10)
	private String toughness;
	
	@Column(name = "card_set", nullable = false, length = 40)
	private String cardSet;
	
	@Column(name = "card_type", nullable = false, length = 100)
	private String cardType;
	
	@Column(name = "card_number", nullable = false, length = 10)
	private String cardNumber;
	
	@Column(name = "legal", nullable = false)
	private boolean legal;
	
	@Column(name = "rarity", nullable = false, length = 10)
	private String rarity;
	
	@Column(name = "name", nullable = false, length = 200)
	private String name;
}
