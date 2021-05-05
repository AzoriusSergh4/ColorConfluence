package com.cc.security.user.token;

import com.cc.security.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenId;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        token = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTokenId() {
        return tokenId;
    }
}
