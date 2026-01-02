/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.mycompany.andrijana_automobili.dto.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @author HP
 */
public class ModelDto implements Dto{
    private Long id;
//    private MarkaDto marka;
    private MarkaDto marka;
    @NotBlank(message = "Model je obavezan.")
    private String model;
    

    public ModelDto() {
    }

//    public ModelDto(Long id, MarkaDto marka, String model) {
//        this.id = id;
//        this.marka = marka;
//        this.model = model;
//    }

    public ModelDto(Long id, MarkaDto marka, String model) {
        this.id = id;
        this.marka = marka;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public MarkaDto getMarka() {
//        return marka;
//    }
//
//    public void setMarka(MarkaDto marka) {
//        this.marka = marka;
//    }

    public MarkaDto getMarka() {
        return marka;
    }

    public void setMarka(MarkaDto marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    
    
    
}
