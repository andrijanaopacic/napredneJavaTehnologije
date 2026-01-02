package com.mycompany.andrijana_automobili.mapper.impl;

import com.mycompany.andrijana_automobili.dto.impl.AutomobilDto;
import com.mycompany.andrijana_automobili.dto.impl.ModelDto;
import com.mycompany.andrijana_automobili.entity.impl.Automobil;
import com.mycompany.andrijana_automobili.mapper.DtoEntityMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AutomobilMapper implements DtoEntityMapper<AutomobilDto, Automobil> {

    private final ModelMapper modelMapper;

    public AutomobilMapper(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AutomobilDto toDto(Automobil e) {
        if (e == null) return null;
        ModelDto modelDto = e.getModel() != null ? modelMapper.toDto(e.getModel()) : null;

        return new AutomobilDto(
                e.getId(),
                e.getCena(),
                e.getGodiste(),
                e.getKilometraza(),
                e.getSnaga(),
                e.getKubikaza(),
                e.getSlika(),
                e.getGorivo(),
                e.getMenjac(),
                modelDto
        );
    }

    @Override
    public Automobil toEntity(AutomobilDto t) {
        if (t == null) return null;

        Automobil a = new Automobil();
        a.setId(t.getId());
        a.setCena(t.getCena());
        a.setGodiste(t.getGodiste());
        a.setKilometraza(t.getKilometraza());
        a.setSnaga(t.getSnaga());
        a.setKubikaza(t.getKubikaza());
        a.setSlika(t.getSlika());
        a.setGorivo(t.getGorivo());
        a.setMenjac(t.getMenjac());

        if (t.getModel() != null) {
            a.setModel(modelMapper.toEntity(t.getModel()));
        }

        return a;
    }
}