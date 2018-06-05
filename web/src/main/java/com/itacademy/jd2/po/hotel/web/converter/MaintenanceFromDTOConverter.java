package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.web.dto.MaintenanceDTO;

@Component
public class MaintenanceFromDTOConverter implements Function<MaintenanceDTO, IMaintenance> {

    @Autowired
    private IMaintenanceService maintenanceService;

    @Override
    public IMaintenance apply(final MaintenanceDTO dto) {
        final IMaintenance entity = maintenanceService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setActualPrice(dto.getActualPrice());
        entity.setAvailable(dto.isAvailable());
        return entity;
    }
}
