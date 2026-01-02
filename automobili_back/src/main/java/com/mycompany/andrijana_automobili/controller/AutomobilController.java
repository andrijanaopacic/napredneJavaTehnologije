/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.controller;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
import com.mycompany.andrijana_automobili.service.AutomobilService;
import com.mycompany.andrijana_automobili.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/automobil")
public class AutomobilController {
    
    private final AutomobilService automobilService;

    public AutomobilController(AutomobilService automobilService) {
        this.automobilService = automobilService;
    }
    
    @GetMapping()
    @Operation(summary = "Vrati sve automobile.")
    public ResponseEntity<List<AutomobilDto>> getAll(){
        
        return new ResponseEntity<>(automobilService.findAll(),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AutomobilDto> getById(
            @NotNull(message = "Ne bi trebalo da bude null ili prazno.")
            @PathVariable(value = "id") Long id){
        try {
            return new ResponseEntity<>(automobilService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AutomobilController greska");
        }
    }
    
    @PostMapping
    @Operation(summary = "Kreiraj novi automobil.")
    public ResponseEntity<AutomobilDto> addAutomobil(
            @Valid @RequestBody AutomobilDto automobilDto){
        try {
            AutomobilDto saved = automobilService.create(automobilDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom cuvanja automobila.");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id){
        try {
            automobilService.deleteById(id);
            return new ResponseEntity<>("Automobil uspesno obrisan.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Automobil ne postoji: "+id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Promeni postojeci automobil.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = ModelDto.class), mediaType = "application/json")
    })
    public ResponseEntity<AutomobilDto> updateAutomobil(
            @PathVariable Long id,
            @Valid @RequestBody AutomobilDto automobilDto){
        try {
            automobilDto.setId(id);
            AutomobilDto updated = automobilService.update(id, automobilDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Greska pri azuriranju automobila: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom promene automobila.");
        }
    }
    
}
