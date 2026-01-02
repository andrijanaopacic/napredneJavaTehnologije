/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.mapper.impl;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.OglasDto;
import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import com.mycompany.andrijana_automobili.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class OglasMapper implements DtoEntityMapper<OglasDto, Oglas> {

    private final AutomobilMapper automobilMapper;

    public OglasMapper(AutomobilMapper automobilMapper) {
        this.automobilMapper = automobilMapper;
    }

    @Override
    public OglasDto toDto(Oglas e) {
        if (e == null) return null;

        AutomobilDto automobilDto = e.getAutomobil() != null
                ? automobilMapper.toDto(e.getAutomobil())
                : null;

        return new OglasDto(
                e.getId(),
                e.getNaslov(),
                e.getOpis(),
                e.getVremeOglasavanja(),
                e.getUkupnaCena(),
                e.isAktivan(),
                automobilDto,
                e.getKorisnik(), // čitav korisnik
                e.isSacuvan()
        );
    }

    @Override
    public Oglas toEntity(OglasDto t) {
        if (t == null) return null;

        Oglas oglas = new Oglas();
        oglas.setId(t.getId());
        oglas.setNaslov(t.getNaslov());
        oglas.setOpis(t.getOpis());
        oglas.setVremeOglasavanja(t.getVremeOglasavanja());
        oglas.setUkupnaCena(t.getUkupnaCena());
        oglas.setAktivan(t.isAktivan());

        if (t.getAutomobil() != null && t.getAutomobil().getId() != null) {
            Automobil auto = new Automobil();
            auto.setId(t.getAutomobil().getId());
            oglas.setAutomobil(auto);
        }

        // korisnik se postavlja u servisu na trenutno ulogovanog
        oglas.setKorisnik(t.getKorisnik());

        // transient sacuvan polje
        oglas.setSacuvan(t.isSacuvan());

        return oglas;
    }
}
