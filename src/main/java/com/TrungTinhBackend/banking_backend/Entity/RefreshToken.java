package com.TrungTinhBackend.banking_backend.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Date expiraDate;

    public RefreshToken() {
    }

    public RefreshToken(Long id, String token, User user, Date expiraDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiraDate = expiraDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiraDate() {
        return expiraDate;
    }

    public void setExpiraDate(Date expiraDate) {
        this.expiraDate = expiraDate;
    }
}
