package com.cc.web.entity;

import com.cc.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class DeckFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Deck> decks;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeckFolder> folders;

    @JsonIgnore
    @ManyToOne
    private DeckFolder parent;

    private boolean root;

    @JsonIgnore
    @ManyToOne
    private User user;

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

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public List<DeckFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<DeckFolder> folders) {
        this.folders = folders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public DeckFolder getParent() {
        return parent;
    }

    public void setParent(DeckFolder parent) {
        this.parent = parent;
    }


}
