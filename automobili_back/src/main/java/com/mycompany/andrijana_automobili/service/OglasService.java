package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.OglasDto;
import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import com.mycompany.andrijana_automobili.entity.impl.SacuvaniOglas;
import com.mycompany.andrijana_automobili.entity.impl.Uloga;
import com.mycompany.andrijana_automobili.mapper.impl.AutomobilMapper;
import com.mycompany.andrijana_automobili.mapper.impl.OglasMapper;
import com.mycompany.andrijana_automobili.repository.impl.AutomobilRepository;
import com.mycompany.andrijana_automobili.repository.impl.ModelRepository;
import com.mycompany.andrijana_automobili.repository.impl.OglasRepository;
import com.mycompany.andrijana_automobili.repository.impl.SacuvaniOglasRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OglasService {

    private final OglasMapper oglasMapper;
    private final OglasRepository oglasRepository;
    private final AutomobilRepository automobilRepository;
    private final SacuvaniOglasRepository sacuvaniOglasRepository;
    private final ModelRepository modelRepository;
    private final AutomobilService automobilService;
    private final AutomobilMapper automobilMappper;

    @Autowired
    public OglasService(OglasMapper oglasMapper, OglasRepository oglasRepository, AutomobilRepository automobilRepository, SacuvaniOglasRepository sacuvaniOglasRepository, ModelRepository modelRepository, AutomobilService automobilService, AutomobilMapper automobilMappper) {
        this.oglasMapper = oglasMapper;
        this.oglasRepository = oglasRepository;
        this.automobilRepository = automobilRepository;
        this.sacuvaniOglasRepository = sacuvaniOglasRepository;
        this.modelRepository = modelRepository;
        this.automobilService = automobilService;
        this.automobilMappper = automobilMappper;
    }

    

    public List<OglasDto> findAll() {
        return oglasRepository.findAll()
                .stream()
                .map(oglasMapper::toDto)
                .collect(Collectors.toList());
    }

    public OglasDto findById(Long id) throws Exception {
        Oglas oglas = oglasRepository.findById(id);
        if (oglas == null) throw new Exception("Oglas sa ID " + id + " ne postoji.");
        return oglasMapper.toDto(oglas);
    }

    @Transactional
    public OglasDto create(OglasDto dto, Korisnik trenutniKorisnik) throws Exception {
        dto.setId(null); // ukloni ID ako je poslat
        Oglas oglas = oglasMapper.toEntity(dto);

        // postavi automobil
        if (dto.getAutomobil() != null && dto.getAutomobil().getId() != null) {
            Automobil auto = automobilRepository.findById(dto.getAutomobil().getId());
            if (auto == null) throw new Exception("Automobil sa ID " + dto.getAutomobil().getId() + " ne postoji.");
            oglas.setAutomobil(auto);
        }

        // postavi korisnika
        oglas.setKorisnik(trenutniKorisnik);

        oglasRepository.save(oglas);
        return oglasMapper.toDto(oglas);
    }

    @Transactional
    public OglasDto update(Long id, OglasDto dto, Korisnik trenutniKorisnik) throws Exception {
        
        Oglas existing = oglasRepository.findById(id);

        existing.setNaslov(dto.getNaslov());
        existing.setOpis(dto.getOpis());
        existing.setUkupnaCena(dto.getUkupnaCena());
        existing.setAktivan(dto.isAktivan());

        if (dto.getAutomobil() != null && dto.getAutomobil().getId() != null) {
            AutomobilDto updatedAuto = automobilService.update(dto.getAutomobil().getId(), dto.getAutomobil());
            // Ako želiš da osvežiš entitet oglasa sa novim autom, možeš i ovo:
            existing.setAutomobil(automobilMappper.toEntity(updatedAuto));
        }

        oglasRepository.save(existing);

        return oglasMapper.toDto(existing);
    }

    @Transactional
    public void deleteById(Long id, Korisnik trenutniKorisnik) throws Exception {
        Oglas existing = oglasRepository.findById(id);
        if (existing == null) throw new Exception("Oglas sa ID " + id + " ne postoji.");

        if (!existing.getKorisnik().getId().equals(trenutniKorisnik.getId()) &&
            trenutniKorisnik.getUloga()!= Uloga.ADMIN) {
            throw new Exception("Nemate prava da obrišete ovaj oglas.");
        }

        oglasRepository.deleteById(id);
    }

    @Transactional
    public Oglas getOglasEntityById(Long oglasId) throws Exception {
        return oglasRepository.findById(oglasId);
    }
    
    
    // ---------------- MOJI OGLASI ----------------
    @Transactional
    public List<OglasDto> vratiMojeOglase(Korisnik korisnik) {
        return sacuvaniOglasRepository.findByKorisnik(korisnik)
                .stream()
                .map(SacuvaniOglas::getOglas)
                .map(oglasMapper::toDto)
                .collect(Collectors.toList());
    }

    // ---------------- SACUVANI OGLASI ----------------
    @Transactional
    public List<OglasDto> vratiSacuvaneOglase(Korisnik korisnik) {
        return sacuvaniOglasRepository.findByKorisnikAndOglasAktivan(korisnik, true)
                .stream()
                .map(SacuvaniOglas::getOglas)
                .map(oglasMapper::toDto)
                .collect(Collectors.toList());
    }

    // ---------------- SACUVAJ OGLAS ----------------
    @Transactional
    public String sacuvajOglas(Korisnik korisnik, Long oglasId) throws Exception {
        Oglas oglas = oglasRepository.findById(oglasId);
        if (oglas == null) return "Oglas ne postoji";

        // Proveri da li je već sacuvan
        Optional<SacuvaniOglas> postoji = sacuvaniOglasRepository.findByKorisnikAndOglas(korisnik, oglas);
        if (postoji.isPresent()) return "Oglas je već sacuvan";

        SacuvaniOglas so = new SacuvaniOglas(korisnik, oglas);
        sacuvaniOglasRepository.save(so);
        return "Oglas uspesno sacuvan";
    }

    // ---------------- IZBRISI SACUVANI OGLAS ----------------
    @Transactional
    public String obrisiSacuvaniOglas(Korisnik korisnik, Long oglasId) throws Exception {
        Oglas oglas = oglasRepository.findById(oglasId);
        if (oglas == null) return "Oglas ne postoji";

        // koristi metodu koju već imaš u repozitorijumu
        sacuvaniOglasRepository.deleteByKorisnikAndOglas(korisnik, oglas);

        return "Oglas uspesno obrisan iz sacuvanih";
    }

    @Transactional
    public OglasDto createOglas(OglasDto dto, Korisnik trenutni) throws Exception {
        // Kreiraj automobil
        Automobil auto = new Automobil();
        auto.setCena(dto.getAutomobil().getCena());
        auto.setGodiste(dto.getAutomobil().getGodiste());
        auto.setGorivo(dto.getAutomobil().getGorivo());
        auto.setMenjac(dto.getAutomobil().getMenjac());
        auto.setSnaga(dto.getAutomobil().getSnaga());
        auto.setKubikaza(dto.getAutomobil().getKubikaza());
        auto.setKilometraza(dto.getAutomobil().getKilometraza());
        auto.setSlika(dto.getAutomobil().getSlika());
        auto.setModel(modelRepository.findById(dto.getAutomobil().getModel().getId()));

        automobilRepository.save(auto);

        // Kreiraj oglas
        Oglas oglas = new Oglas();
        oglas.setNaslov(dto.getNaslov());
        oglas.setOpis(dto.getOpis());
        oglas.setVremeOglasavanja(new Date());
        oglas.setUkupnaCena(dto.getUkupnaCena());
        oglas.setAktivan(dto.isAktivan());
        oglas.setKorisnik(trenutni);
        oglas.setAutomobil(auto);

        oglasRepository.save(oglas);

        return oglasMapper.toDto(oglas);
    }

    
    
}