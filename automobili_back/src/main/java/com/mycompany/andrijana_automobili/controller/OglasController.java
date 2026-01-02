/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.controller;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
import com.mycompany.andrijana_automobili.dto.impl.OglasDto;
import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Korisnik;
import com.mycompany.andrijana_automobili.entity.impl.Oglas;
import com.mycompany.andrijana_automobili.mapper.impl.OglasMapper;
import com.mycompany.andrijana_automobili.repository.impl.AutomobilRepository;
import com.mycompany.andrijana_automobili.repository.impl.KorisnikRepository;
import com.mycompany.andrijana_automobili.repository.impl.ModelRepository;
import com.mycompany.andrijana_automobili.repository.impl.OglasRepository;
import com.mycompany.andrijana_automobili.service.AuthService;
import com.mycompany.andrijana_automobili.service.OglasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author HP
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/oglas")
public class OglasController {

    private final OglasService oglasService;
    private final AutomobilRepository automobilRepository;
    private final ModelRepository modelRepository;
    private final AuthService authService;
    private final OglasMapper oglasMapper;
    private final KorisnikRepository korisnikRepository;
    private final OglasRepository oglasRepository;

    public OglasController(OglasService oglasService, AutomobilRepository automobilRepository, ModelRepository modelRepository, AuthService authService, OglasMapper oglasMapper, KorisnikRepository korisnikRepository, OglasRepository oglasRepository) {
        this.oglasService = oglasService;
        this.automobilRepository = automobilRepository;
        this.modelRepository = modelRepository;
        this.authService = authService;
        this.oglasMapper = oglasMapper;
        this.korisnikRepository = korisnikRepository;
        this.oglasRepository = oglasRepository;
    }

    
    private Korisnik getTrenutniKorisnik() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName().equals("anonymousUser")) {
            return null; // ili baci exception ako mora biti ulogovan
        }
        return korisnikRepository.findByUsername(auth.getName());
    
    }

    @GetMapping()
    public ResponseEntity<List<OglasDto>> getAll() {
        return new ResponseEntity<>(oglasService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OglasDto> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(oglasService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<OglasDto> addOglas(@RequestBody OglasDto oglasDto) throws Exception {
        Korisnik trenutni = authService.getTrenutniKorisnik();
        if (trenutni == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        OglasDto kreiranOglas = oglasService.createOglas(oglasDto, trenutni);

        return new ResponseEntity<>(kreiranOglas, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OglasDto> updateOglas(@PathVariable Long id, @Valid @RequestBody OglasDto oglasDto) {
        try {
            oglasDto.setId(id);
            if (oglasDto.getAutomobil() != null) {
                oglasDto.setUkupnaCena(oglasDto.getAutomobil().getCena());
            }

            OglasDto updated = oglasService.update(id, oglasDto, getTrenutniKorisnik());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            oglasService.deleteById(id, getTrenutniKorisnik());
            return new ResponseEntity<>("Oglas uspesno obrisan.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Greska: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/moji")
    public ResponseEntity<List<OglasDto>> vratiMojeOglase() {
        Korisnik korisnik = getTrenutniKorisnik();
        List<OglasDto> oglasi = oglasService.vratiMojeOglase(korisnik);
        return new ResponseEntity<>(oglasi, HttpStatus.OK);
    }
    
    @GetMapping("/sacuvani")
    public ResponseEntity<List<OglasDto>> vratiSacuvaneOglase() {
        Korisnik korisnik = getTrenutniKorisnik();
        List<OglasDto> oglasi = oglasService.vratiSacuvaneOglase(korisnik);
        return new ResponseEntity<>(oglasi, HttpStatus.OK);
    }
    
    @PostMapping("/sacuvaj/{id}")
    public ResponseEntity<String> sacuvajOglas(@PathVariable Long id) throws Exception {
        Korisnik korisnik = getTrenutniKorisnik();
        String poruka = oglasService.sacuvajOglas(korisnik, id);
        return new ResponseEntity<>(poruka, HttpStatus.OK);
    }
    
    @DeleteMapping("/sacuvani/{id}")
    public ResponseEntity<String> izbrisiSacuvanOglas(@PathVariable Long id) throws Exception {
        Korisnik korisnik = getTrenutniKorisnik();
        String poruka = oglasService.obrisiSacuvaniOglas(korisnik, id);
        return new ResponseEntity<>(poruka, HttpStatus.OK);
    }
    
}
