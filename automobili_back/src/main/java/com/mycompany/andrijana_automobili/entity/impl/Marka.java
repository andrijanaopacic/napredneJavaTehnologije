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
@Table(name = "marka")
public class Marka implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy="marka", cascade = CascadeType.ALL)
    private List<Model> modeli = new ArrayList<>();
    
    private String marka;

    public Marka() {
    }

    public Marka(Long id, String marka) {
        this.id = id;
        this.marka = marka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public List<Model> getModeli() {
        return modeli;
    }

    public void setModeli(List<Model> modeli) {
        this.modeli = modeli;
    }
    
    
    
    
}
