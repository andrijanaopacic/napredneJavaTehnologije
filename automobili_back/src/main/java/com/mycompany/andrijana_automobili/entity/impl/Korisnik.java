/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import com.mycompany.andrijana_automobili.entity.MyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "korisnici", uniqueConstraints = {
    @UniqueConstraint(name = "uk_korisnicko_ime", columnNames = "username"),
    @UniqueConstraint(name = "uk_email", columnNames = "email")
})
public class Korisnik implements MyEntity{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false, length=50)
    private String username;

    @Column(nullable=false, length=120)
    private String email;

    @Column(nullable=false)
    private String passwordHash; 

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Uloga uloga = Uloga.KORISNIK;

    @Column(nullable=false)
    private boolean enabled = false;

    public Korisnik() {
    }

    public Korisnik(Long id) {
        this.id = id;
    }
    
    public Korisnik(Long id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
    
    
}
