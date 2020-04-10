package com.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.magicthegathering.javasdk.resource.Legality;

@Entity
public class CardCC {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double cmc;

	@Size(max=100)
	private String manaCost;
	
	private String power;

	private String toughness;

	private String cardSet;

	private String cardType;

	private String cardNumber;

	private boolean legal;

	private String rarity;
	
	private String name;
	
	@OneToMany(mappedBy = "card")
	private List<CardTranslation> cardTranslation;
	
	public CardCC () {
		
	}
	
	public CardCC (io.magicthegathering.javasdk.resource.Card card) {
		this.cmc = card.getCmc();
		this.manaCost = card.getManaCost();
		this.power = card.getPower();
		this.toughness = card.getToughness();
		this.cardSet = card.getSet();
		this.cardType = card.getType();
		this.cardNumber = card.getNumber();
		Legality[] legalities = card.getLegalities();
		this.legal = false;
		for(int i = 0; i < legalities.length && !legal; i++) {
			if(legalities[i].getFormat().equals("Standard") && legalities[i].getLegality().equals("Legal")) {
				this.legal = true;
			}
		}
		this.rarity = card.getRarity();
		this.name = card.getName();
		
	}

	public long getId() {
		return id;
	}

	public double getCmc() {
		return cmc;
	}

	public void setCmc(double cmc) {
		this.cmc = cmc;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getCardSet() {
		return cardSet;
	}

	public void setCardSet(String cardSet) {
		this.cardSet = cardSet;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public boolean isLegal() {
		return legal;
	}

	public void setLegal(boolean legal) {
		this.legal = legal;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CardTranslation> getCardTranslation() {
		return cardTranslation;
	}

	public void setCardTranslation(List<CardTranslation> cardTranslation) {
		this.cardTranslation = cardTranslation;
	}

}
