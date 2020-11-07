package com.cc.web.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class CardCC {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double cmc;

	private String manaCost;
	
	private String power;

	private String toughness;

	private String cardType;

	private String rarity;
	
	private String name;
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CardTranslation> cardTranslation;

	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CardLegality> legalities;
	
	public CardCC () {
		
	}
	
	public CardCC (io.magicthegathering.javasdk.resource.Card card) {
		this.cmc = card.getCmc();
		this.manaCost = card.getManaCost();
		this.power = card.getPower();
		this.toughness = card.getToughness();
		this.cardType = card.getType();

		this.legalities = new ArrayList<>();


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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public List<CardLegality> getLegalities() {
		return legalities;
	}

	public void setLegalities(List<CardLegality> legalities) {
		this.legalities = legalities;
	}
}
