/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.entity.impl;

import com.mycompany.andrijana_automobili.entity.MyEntity;
import jakarta.persistence.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HP
 */

@Entity
@Table(name = "model")
public class Model implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "marka_id", nullable = false)
    private Marka marka;
    private String model;
    
    @OneToMany(mappedBy="model", cascade = CascadeType.ALL)
    private List<Automobil> automobili = new ArrayList<>();
    

    public Model() {
    }

    public Model(Long id, Marka marka, String model) {
        this.id = id;
        this.marka = marka;
        this.model = model;
    }

    public Marka getMarka() {
        return marka;
    }

    public void setMarka(Marka marka) {
        this.marka = marka;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public List<Automobil> getAutomobili() {
        return automobili;
    }

    public void setAutomobili(List<Automobil> automobili) {
        this.automobili = automobili;
    }

    

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    
    
    
}
