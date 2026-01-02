package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.SacuvaniOglas;
import com.mycompany.andrijana_automobili.repository.impl.OglasRepository;
import com.mycompany.andrijana_automobili.repository.impl.SacuvaniOglasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SacuvaniOglasService {

    private final SacuvaniOglasRepository omiljeniOglasRepository;
    private final OglasRepository oglasRepository;

    public SacuvaniOglasService(SacuvaniOglasRepository omiljeniOglasRepository,
                                OglasRepository oglasRepository) {
        this.omiljeniOglasRepository = omiljeniOglasRepository;
        this.oglasRepository = oglasRepository;
    }

    @Transactional
    public String dodajOmiljeni(Long oglasId, Korisnik korisnik) throws Exception {
        Oglas oglas = oglasRepository.findById(oglasId);
        
        if (omiljeniOglasRepository.findByKorisnikAndOglas(korisnik, oglas).isPresent()) {
            return "Oglas je već u omiljenim";
        }

        SacuvaniOglas om = new SacuvaniOglas(korisnik, oglas);
        omiljeniOglasRepository.save(om);
        return "Oglas dodat u omiljene";
    }

    @Transactional
    public String ukloniOmiljeni(Long oglasId, Korisnik korisnik) throws Exception {
        Oglas oglas = oglasRepository.findById(oglasId);

        omiljeniOglasRepository.deleteByKorisnikAndOglas(korisnik, oglas);
        return "Oglas uklonjen iz omiljenih";
    }

    @Transactional(readOnly = true)
    public List<Oglas> vratiOmiljene(Korisnik korisnik) {
        return omiljeniOglasRepository.findByKorisnik(korisnik)
                .stream()
                .map(SacuvaniOglas::getOglas)
                .collect(Collectors.toList());
    }
}