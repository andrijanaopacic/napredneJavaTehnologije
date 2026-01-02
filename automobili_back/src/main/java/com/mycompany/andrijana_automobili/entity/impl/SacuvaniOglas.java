/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "sacuvani_oglas")
public class SacuvaniOglas {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Korisnik korisnik;
    
    @ManyToOne
    private Oglas oglas;

    public SacuvaniOglas() {
    }
    
    public SacuvaniOglas(Korisnik korisnik, Oglas oglas) {
        this.korisnik = korisnik;
        this.oglas = oglas;
    }
    
    
    public SacuvaniOglas(Long id, Korisnik korisnik, Oglas oglas) {
        this.id = id;
        this.korisnik = korisnik;
        this.oglas = oglas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }
    
    
}
