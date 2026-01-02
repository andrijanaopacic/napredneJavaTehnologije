package com.mycompany.andrijana_automobili.mapper.impl;

import com.mycompany.andrijana_automobili.dto.impl.MarkaDto;
import com.mycompany.andrijana_automobili.entity.impl.Marka;
import com.mycompany.andrijana_automobili.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class MarkaMapper implements DtoEntityMapper<MarkaDto, Marka> {

    @Override
    public MarkaDto toDto(Marka e) {
        if (e == null) return null;
        return new MarkaDto(e.getId(), e.getMarka());
    }

    @Override
    public Marka toEntity(MarkaDto t) {
        if (t == null) return null;
        Marka marka = new Marka();
        marka.setId(t.getId());
        marka.setMarka(t.getMarka());
        return marka;
    }
}