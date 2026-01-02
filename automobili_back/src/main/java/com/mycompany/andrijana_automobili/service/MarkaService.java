/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.mapper.impl.MarkaMapper;
import com.mycompany.andrijana_automobili.repository.impl.MarkaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class MarkaService {
    
    private final MarkaRepository markaRepository;
    private final MarkaMapper markaMapper;

    @Autowired
    public MarkaService(MarkaRepository markaRepository, MarkaMapper markaMapper) {
        this.markaRepository = markaRepository;
        this.markaMapper = markaMapper;
    }
    
    public List<MarkaDto> findAll(){
        return markaRepository.findAll()
                .stream()
                .map(markaMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public MarkaDto findById(Long id) throws Exception {
        return markaMapper.toDto(markaRepository.findById(id));
    }

    @Transactional
    public MarkaDto create(MarkaDto dto) {
        Marka marka = markaMapper.toEntity(dto);
        markaRepository.save(marka);
        return markaMapper.toDto(marka);
    }

    @Transactional
    public void deleteById(Long id) {
        markaRepository.deleteById(id);
    }

    @Transactional
    public MarkaDto update(MarkaDto dto) {
        Marka updated = markaMapper.toEntity(dto);
        markaRepository.save(updated);
        return markaMapper.toDto(updated);
    }

    @Transactional
    public Marka getMarkaEntityById(Long markaId) throws Exception {
        return markaRepository.findById(markaId);
    }
    
    
    
}
