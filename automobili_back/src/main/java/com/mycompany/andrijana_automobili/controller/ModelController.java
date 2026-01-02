/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.controller;

import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
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
@RequestMapping("/api/model")
public class ModelController {
    
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }
    
    @GetMapping()
    @Operation(summary = "Vrati sve modele.")
    public ResponseEntity<List<ModelDto>> getAll(){
        return new ResponseEntity<>(modelService.findAll(),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ModelDto> getById(
            @NotNull(message = "Ne bi trebalo da bude null ili prazno.")
            @PathVariable(value = "id") Long id){
        try {
            return new ResponseEntity<>(modelService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MarkaController greska");
        }
    }
    
    @PostMapping
    @Operation(summary = "Kreiraj novi model.")
    public ResponseEntity<ModelDto> addModel(
            @Valid @RequestBody ModelDto modelDto){
        try {
            ModelDto saved = modelService.create(modelDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom cuvanja modela.");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id){
        try {
            modelService.deleteById(id);
            return new ResponseEntity<>("Model uspesno obrisan.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Model ne postoji: "+id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Promeni postojeci model.")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = ModelDto.class), mediaType = "application/json")
    })
    public ResponseEntity<ModelDto> updateModel(
            @PathVariable Long id,
            @Valid @RequestBody ModelDto modelDto){
        try {
            modelDto.setId(id);
            ModelDto updated = modelService.update(modelDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska prilikom promene modela.");
        }
    }
    
}
