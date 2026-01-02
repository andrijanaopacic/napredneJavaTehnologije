/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.mycompany.andrijana_automobili.dto.Dto;
import com.mycompany.andrijana_automobili.entity.impl.Model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class MarkaDto implements Dto{
    private Long id;
//    @Valid
//    private List<ModelDto> modeli = new ArrayList<>();
    @NotBlank(message = "Marka je obavezna.")
    private String marka;

    public MarkaDto() {
    }

//    public MarkaDto(Long id, String marka, List<ModelDto> modeli) {
//        this.id = id;
//        this.marka = marka;
//        this.modeli = modeli;
//    }

    public MarkaDto(Long id, String marka) {
        this.id = id;
        this.marka = marka;
    }
    
    

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<ModelDto> getModeli() {
//        return modeli;
//    }
//
//    public void setModeli(List<ModelDto> modeli) {
//        this.modeli = modeli;
//    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }
    
    
}
