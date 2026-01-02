package com.mycompany.andrijana_automobili.mapper.impl;

import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.entity.impl.Model;
import com.mycompany.andrijana_automobili.mapper.DtoEntityMapper;
import com.mycompany.andrijana_automobili.service.MarkaService;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper implements DtoEntityMapper<ModelDto, Model> {

    private final MarkaService markaService;

    public ModelMapper(MarkaService markaService) {
        this.markaService = markaService;
    }

    @Override
    public ModelDto toDto(Model e) {
        if (e == null) return null;

        // Ovde je ključno: koristimo stvarni naziv marke, ne id
        MarkaDto markaDto = e.getMarka() != null
                ? new MarkaDto(e.getMarka().getId(), e.getMarka().getMarka())
                : null;

        return new ModelDto(e.getId(), markaDto, e.getModel());
    }

    @Override
    public Model toEntity(ModelDto t) {
        if (t == null) return null;

        Model model = new Model();
        model.setId(t.getId());
        model.setModel(t.getModel());

        if (t.getMarka() != null) {
            try {
                Marka marka = markaService.getMarkaEntityById(t.getMarka().getId());
                model.setMarka(marka);
            } catch (Exception e) {
                throw new RuntimeException("Greška prilikom pronalazenja marke: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Model mora imati referencu na marku.");
        }

        return model;
    }
}