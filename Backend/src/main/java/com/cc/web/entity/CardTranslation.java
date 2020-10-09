package com.cc.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class CardTranslation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@Size(max=1000)
	private String description;
	
	@Size(max=1000)
	private String lore;
	
	private String url;
	
	private String lang;
	
	@ManyToOne
	@JoinColumn(name="card_id")
	private CardCC card;
	
	public CardTranslation() {
		
	}
	
	public CardTranslation(String name, String description, String lore, String url, String lang) {
		this.name = name;
		this.description = description;
		this.lore = lore;
		this.url = url;
		this.lang = lang;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public CardCC getCard() {
		return card;
	}

	public void setCard(CardCC card) {
		this.card = card;
	}
	
	
	
}
