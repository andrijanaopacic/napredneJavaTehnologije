/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.entity.impl.Model;
import com.mycompany.andrijana_automobili.mapper.impl.AutomobilMapper;
import com.mycompany.andrijana_automobili.mapper.impl.MarkaMapper;
import com.mycompany.andrijana_automobili.repository.impl.AutomobilRepository;
import com.mycompany.andrijana_automobili.repository.impl.MarkaRepository;
import com.mycompany.andrijana_automobili.repository.impl.ModelRepository;
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
public class AutomobilService {
    
    private final AutomobilRepository automobilRepository;
    private final AutomobilMapper automobilMapper;
    private final ModelRepository modelRepository;

    @Autowired
    public AutomobilService(AutomobilRepository automobilRepository, AutomobilMapper automobilMapper, ModelRepository modelRepository) {
        this.automobilRepository = automobilRepository;
        this.automobilMapper = automobilMapper;
        this.modelRepository = modelRepository;
    }
    
    public List<AutomobilDto> findAll(){
        return automobilRepository.findAll()
                .stream()
                .map(automobilMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public AutomobilDto findById(Long id) throws Exception {
        return automobilMapper.toDto(automobilRepository.findById(id));
    }

    @Transactional
    public AutomobilDto create(AutomobilDto dto) throws Exception {
        dto.setId(null); 
        Automobil automobil = automobilMapper.toEntity(dto);

        if (dto.getModel() != null && dto.getModel().getId() != null) {
            Model model = modelRepository.findById(dto.getModel().getId());
            automobil.setModel(model);
        }

        automobilRepository.save(automobil);
        return automobilMapper.toDto(automobil);
    }

    @Transactional
    public void deleteById(Long id) {
        automobilRepository.deleteById(id);
    }

    @Transactional
    public AutomobilDto update(Long id, AutomobilDto dto) throws Exception {
        Automobil existing = automobilRepository.findById(id);

        existing.setCena(dto.getCena());
        existing.setGodiste(dto.getGodiste());
        existing.setKilometraza(dto.getKilometraza());
        existing.setSnaga(dto.getSnaga());
        existing.setKubikaza(dto.getKubikaza());
        existing.setGorivo(dto.getGorivo());
        existing.setMenjac(dto.getMenjac());
        existing.setSlika(dto.getSlika());

        if (dto.getModel() != null && dto.getModel().getId() != null) {
            Model model = modelRepository.findById(dto.getModel().getId());
            existing.setModel(model);
        }

        automobilRepository.save(existing);
        return automobilMapper.toDto(existing);
    }

    @Transactional
    public Automobil getAutomobilEntityById(Long automobilId) throws Exception {
        return automobilRepository.findById(automobilId);
    }
    
}
