/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "oglas")
public class Oglas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naslov;
    private String opis;
    private Date vremeOglasavanja;
    private double ukupnaCena;
    private boolean aktivan;
    
    @ManyToOne
    @JoinColumn(name = "automobil_id", nullable = false)
    private Automobil automobil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private Korisnik korisnik; // korisnik koji je postavio oglas

    @Transient
    private boolean sacuvan; 
    
    public Oglas() {
    }

    public Oglas(Long id, String naslov, String opis, Date vremeOglasavanja, double ukupnaCena, boolean aktivan, Automobil automobil, Korisnik korisnik, boolean sacuvan) {
        this.id = id;
        this.naslov = naslov;
        this.opis = opis;
        this.vremeOglasavanja = vremeOglasavanja;
        this.ukupnaCena = ukupnaCena;
        this.aktivan = aktivan;
        this.automobil = automobil;
        this.korisnik = korisnik;
        this.sacuvan = sacuvan;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public boolean isSacuvan() {
        return sacuvan;
    }

    public void setSacuvan(boolean sacuvan) {
        this.sacuvan = sacuvan;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getVremeOglasavanja() {
        return vremeOglasavanja;
    }

    public void setVremeOglasavanja(Date vremeOglasavanja) {
        this.vremeOglasavanja = vremeOglasavanja;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Automobil getAutomobil() {
        return automobil;
    }

    public void setAutomobil(Automobil automobil) {
        this.automobil = automobil;
    }
    
    
    
}
