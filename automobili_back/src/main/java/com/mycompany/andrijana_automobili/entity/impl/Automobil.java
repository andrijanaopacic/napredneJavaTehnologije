/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import com.mycompany.andrijana_automobili.entity.MyEntity;
import jakarta.persistence.*;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
/**
 *
 * @author HP
 */

@Entity
@Table(name = "automobil")
public class Automobil implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double cena;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "model_id")
    private Model model;
    private int godiste;
    private int kilometraza;
    @Enumerated(EnumType.STRING)
    @Column(name = "gorivo")
    private Gorivo gorivo;
    private int snaga;
    private int kubikaza;
    @Enumerated(EnumType.STRING)
    @Column(name = "menjac")
    private Menjac menjac;
    private String slika;

    public Automobil() {
    }

    public Automobil(Long id, double cena, Model model, int godiste, int kilometraza, Gorivo gorivo, int snaga, int kubikaza, Menjac menjac, String slika) {
        this.id = id;
        this.cena = cena;
        this.model = model;
        this.godiste = godiste;
        this.kilometraza = kilometraza;
        this.gorivo = gorivo;
        this.snaga = snaga;
        this.kubikaza = kubikaza;
        this.menjac = menjac;
        this.slika = slika;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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

    public Gorivo getGorivo() {
        return gorivo;
    }

    public void setGorivo(Gorivo gorivo) {
        this.gorivo = gorivo;
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

    public Menjac getMenjac() {
        return menjac;
    }

    public void setMenjac(Menjac menjac) {
        this.menjac = menjac;
    }

    

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    
    
    
    
}
