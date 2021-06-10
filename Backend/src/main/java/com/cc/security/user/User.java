package com.cc.security.user;

import com.cc.web.entity.DeckFolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<String> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeckFolder> folder;

    public User() {
    }

    public User(RegisterForm data) {
        this.email = data.getEmail();
        this.username = data.getUsername();
        this.password = new BCryptPasswordEncoder().encode(data.getPassword());
        this.enabled = false;
        this.roles = new ArrayList<>();
        this.roles.add("USER");
        var folder = new DeckFolder();
        folder.setName(this.username + "'s Decks");
        folder.setUser(this);
        folder.setRoot(true);
        this.folder = new ArrayList<>();
        this.folder.add(folder);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<DeckFolder> getFolder() {
        return folder;
    }

    public void setFolder(List<DeckFolder> folder) {
        this.folder = folder;
    }
}
