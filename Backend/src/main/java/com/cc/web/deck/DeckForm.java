package com.cc.web.deck;

import com.cc.web.entity.Format;

import java.util.List;

public class DeckForm {

    private long id;

    private String name;

    private List<DeckCardForm> commanders;

    private List<DeckCardForm> main;

    private List<DeckCardForm> sideboard;

    private String comments;

    private Format format;

    private boolean draft;

    public DeckForm() {
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

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
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
