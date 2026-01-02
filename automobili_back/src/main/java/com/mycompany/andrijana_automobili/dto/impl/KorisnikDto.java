/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.dto.impl;

import com.mycompany.andrijana_automobili.dto.Dto;
import com.mycompany.andrijana_automobili.entity.impl.Uloga;

/**
 *
 * @author HP
 */
public class KorisnikDto implements Dto{
    
    private Long id;
    private String username;
    private String email;
    private Uloga uloga;

    public KorisnikDto() {
    }

    public KorisnikDto(Long id, String username, String email, Uloga uloga) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.uloga = uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
    
    
    
    
}
