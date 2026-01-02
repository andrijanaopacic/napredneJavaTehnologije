/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.mycompany.andrijana_automobili.dto.Dto;
import com.mycompany.andrijana_automobili.entity.impl.Gorivo;
import com.mycompany.andrijana_automobili.entity.impl.Menjac;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author HP
 */
public class AutomobilDto implements Dto{
    private Long id;
    @Positive(message = "Cena mora biti pozitivna vrednost.")
    private double cena;
    private int godiste;
    @Min(value = 0, message = "Kilometraža ne može biti negativna.")
    private int kilometraza;
    @Min(value = 1, message = "Snaga mora biti veća od 0.")
    private int snaga;
    private int kubikaza;
    @URL(message = "Slika mora da bude u obliku linka.")
    private String slika;
    private Gorivo gorivo;
    private Menjac menjac;
    private ModelDto model;

    public AutomobilDto() {
    }

    public AutomobilDto(Long id, double cena, int godiste, int kilometraza, int snaga, int kubikaza, String slika, Gorivo gorivo, Menjac menjac, ModelDto model) {
        this.id = id;
        this.cena = cena;
        this.godiste = godiste;
        this.kilometraza = kilometraza;
        this.snaga = snaga;
        this.kubikaza = kubikaza;
        this.slika = slika;
        this.gorivo = gorivo;
        this.menjac = menjac;
        this.model = model;
    }

    

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public int getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(int kilometraza) {
        this.kilometraza = kilometraza;
    }

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    public int getKubikaza() {
        return kubikaza;
    }

    public void setKubikaza(int kubikaza) {
        this.kubikaza = kubikaza;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Gorivo getGorivo() {
        return gorivo;
    }

    public void setGorivo(Gorivo gorivo) {
        this.gorivo = gorivo;
    }

    public Menjac getMenjac() {
        return menjac;
    }

    public void setMenjac(Menjac menjac) {
        this.menjac = menjac;
    }

    public ModelDto getModel() {
        return model;
    }

    public void setModel(ModelDto model) {
        this.model = model;
    }

    
    
    
    
}
