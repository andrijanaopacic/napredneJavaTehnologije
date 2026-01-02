/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Entitet koji predstavlja verifikacioni token povezan sa korisnikom.
 * Koristi se prilikom potvrde email adrese nakon registracije.
 */
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    private String token; // UUID token kao primarni ključ

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Korisnik korisnik;

    @Column(nullable = false)
    private Instant expiresAt;

    public VerificationToken() {}

    /**
     * Kreira novi verifikacioni token za korisnika sa zadatim trajanjem.
     * @param user korisnik kome se dodeljuje token
     * @param ttlSeconds vreme važenja tokena u sekundama
     */
    public static VerificationToken of(Korisnik korisnik, long ttlSeconds) {
        VerificationToken t = new VerificationToken();
        t.token = UUID.randomUUID().toString();
        t.korisnik = korisnik;
        t.expiresAt = Instant.now().plusSeconds(ttlSeconds);
        return t;
    }

    /**
     * Proverava da li je token istekao.
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    // --- GET / SET metode ---

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

   

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    
}
