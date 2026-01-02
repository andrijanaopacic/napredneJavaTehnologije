/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.mapper.impl;

import com.mycompany.andrijana_automobili.dto.impl.KorisnikDto;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class KorisnikMapper implements DtoEntityMapper<KorisnikDto, Korisnik>{

    @Override
    public KorisnikDto toDto(Korisnik e) {
        if(e == null) return null;
        return new KorisnikDto(e.getId(), e.getUsername(), e.getEmail(), e.getUloga());
    }

    @Override
    public Korisnik toEntity(KorisnikDto t) {
        if(t == null) return null;
        Korisnik k = new Korisnik();
        k.setId(t.getId());
        k.setUsername(t.getUsername());
        k.setEmail(t.getEmail());
        k.setUloga(t.getUloga());
        return k;
    }
    
    
    
}
