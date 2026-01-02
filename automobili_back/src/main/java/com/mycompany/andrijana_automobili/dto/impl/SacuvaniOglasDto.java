/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.mycompany.andrijana_automobili.dto.Dto;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author HP
 */
public class SacuvaniOglasDto implements Dto{
    
    private Long id;
    private Korisnik korisnik;
    private Oglas oglas;

    public SacuvaniOglasDto() {
    }

    public SacuvaniOglasDto(Long id, Korisnik korisnik, Oglas oglas) {
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
