/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.controller;

import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.service.MarkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("/api/marka")
public class MarkaController {
    
    private final MarkaService markaService;

    public MarkaController(MarkaService markaService) {
        this.markaService = markaService;
    }
    
    @GetMapping
    @Operation(summary = "Vrati sve marke.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = MarkaDto.class), mediaType = "application/json")
    })
    
    public ResponseEntity<List<MarkaDto>> getAll(){
        return new ResponseEntity<>(markaService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MarkaDto> getById(
            @NotNull(message = "Ne bi trebalo da bude null ili prazno.")
            @PathVariable(value = "id") Long id){
        try {
            return new ResponseEntity<>(markaService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MarkaController greska");
        }
    }
    
    @PostMapping
    @Operation(summary = "Napravi novu marku.")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = MarkaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<MarkaDto> addMarka(@Valid @RequestBody @NotNull MarkaDto markaDto){
        try {
            System.out.println(markaDto);
            MarkaDto saved = markaService.create(markaDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom cuvanja marke.");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id){
        try {
            markaService.deleteById(id);
            return new ResponseEntity<>("Marka uspesno obrisana.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Marka ne postoji: "+id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Promeni postojecu marku.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = MarkaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<MarkaDto> updateMarka(
            @PathVariable Long id,
            @Valid @RequestBody MarkaDto markaDto) {
        try {
            markaDto.setId(id);
            MarkaDto updated = markaService.update(markaDto);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom cuvanja promene marke.");
        }
    }    
}
