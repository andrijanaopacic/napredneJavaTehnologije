/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.util.Date;

/**
 *
 * @author HP
 */
public class OglasDto {
    private Long id;
    private String naslov;
    private String opis;
    @PastOrPresent(message = "Vreme oglasavanja mora biti u proslosti ili sadasnje.")
    private Date vremeOglasavanja;
    @Positive(message = "Cena mora biti pozitivna vrednost.")
    private double ukupnaCena;
    private boolean aktivan;
    private AutomobilDto automobil;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Korisnik korisnik;
    private boolean sacuvan;

    public OglasDto() {
    }

    public OglasDto(Long id, String naslov, String opis, Date vremeOglasavanja, double ukupnaCena, boolean aktivan, AutomobilDto automobil, Korisnik korisnik, boolean sacuvan) {
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

    public AutomobilDto getAutomobil() {
        return automobil;
    }

    public void setAutomobil(AutomobilDto automobil) {
        this.automobil = automobil;
    }

    
    
    
    
    
}
