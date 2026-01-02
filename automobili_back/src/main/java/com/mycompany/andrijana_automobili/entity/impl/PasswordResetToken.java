/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;


import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String token; // UUID kao primarni ključ

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Korisnik korisnik;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    public PasswordResetToken() {}

    private PasswordResetToken(String token, Korisnik korisnik, Instant expiresAt) {
        this.token = token;
        this.korisnik = korisnik;
        this.expiresAt = expiresAt;
        this.used = false;
    }

    public static PasswordResetToken of(Korisnik korisnik, long ttlSeconds) {
        PasswordResetToken t = new PasswordResetToken();
        t.setToken(UUID.randomUUID().toString());
        t.setKorisnik(korisnik);
        t.setExpiresAt(Instant.now().plusSeconds(ttlSeconds));
        t.setUsed(false);
        return t;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    // Getteri i setteri
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
