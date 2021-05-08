package com.cc.web.deck;

import com.cc.web.entity.Format;

import java.util.List;

public class DeckForm {

    private String name;

    private List<DeckCardForm> commanders;

    private List<DeckCardForm> main;

    private List<DeckCardForm> sideboard;

    private String comments;

    private List<Format> formats;

    public DeckForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeckCardForm> getCommanders() {
        return commanders;
    }

    public void setCommanders(List<DeckCardForm> commanders) {
        this.commanders = commanders;
    }

    public List<DeckCardForm> getMain() {
        return main;
    }

    public void setMain(List<DeckCardForm> main) {
        this.main = main;
    }

    public List<DeckCardForm> getSideboard() {
        return sideboard;
    }

    public void setSideboard(List<DeckCardForm> sideboard) {
        this.sideboard = sideboard;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public static class DeckCardForm {
        private long id;
        private int quantity;

        public DeckCardForm() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
