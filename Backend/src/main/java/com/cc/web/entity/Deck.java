package com.cc.web.entity;

import com.cc.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String colors;

    private boolean draft;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DeckCard> commander;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DeckCard> main;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DeckCard> sideboard;

    private String comments;

    @OneToOne
    private Format format;

    @ManyToOne
    private User user;

    public Deck() {}

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public List<DeckCard> getCommander() {
        return commander;
    }

    public void setCommander(List<DeckCard> commander) {
        this.commander = commander;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeckCard> getMain() {
        return main;
    }

    public void setMain(List<DeckCard> main) {
        this.main = main;
    }

    public List<DeckCard> getSideboard() {
        return sideboard;
    }

    public void setSideboard(List<DeckCard> sideboard) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
}
