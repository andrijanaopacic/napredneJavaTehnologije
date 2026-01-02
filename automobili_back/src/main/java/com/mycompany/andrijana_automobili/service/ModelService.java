/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.andrijana_automobili.service;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.entity.impl.Model;
import com.mycompany.andrijana_automobili.mapper.impl.ModelMapper;
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
public class ModelService {
    
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final MarkaRepository markaRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository, ModelMapper modelMapper, MarkaRepository markaRepository) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.markaRepository = markaRepository;
    }
    
    public List<ModelDto> findAll(){
        return modelRepository.findAll()
                .stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ModelDto create(ModelDto dto) throws Exception {
          Model model = modelMapper.toEntity(dto);
          modelRepository.save(model);
          return modelMapper.toDto(model);
//        Marka marka = markaRepository.findById(dto.getMarkaId());
//        if(marka == null){
//            throw new Exception("Marka sa datim ID ne postoji.");
//        }
//        Model model = new Model();
//        model.setModel(dto.getModel());
//        
//        
//        Model model = modelMapper.toEntity(dto);
//        modelRepository.save(model);
//        return modelMapper.toDto(model);
    }

    @Transactional
    public void deleteById(Long id) {
        modelRepository.deleteById(id);
    }

    @Transactional
    public ModelDto update(ModelDto modelDto) {
        Model updated = modelMapper.toEntity(modelDto);
        modelRepository.save(updated);
        return modelMapper.toDto(updated);
    }
    
    public ModelDto findById(Long id) throws Exception {
        return modelMapper.toDto(modelRepository.findById(id));
    }
    
    
}
