package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.web.dto.MaintenanceDTO;

@Component
public class MaintenanceToDTOConverter implements Function<IMaintenance, MaintenanceDTO> {

    @Override
    public MaintenanceDTO apply(final IMaintenance entity) {
        final MaintenanceDTO maintenanceDTO = new MaintenanceDTO();
        maintenanceDTO.setId(entity.getId());
        maintenanceDTO.setName(entity.getName());
        maintenanceDTO.setActualPrice(entity.getActualPrice());
        maintenanceDTO.setAvailable(entity.isAvailable());
        maintenanceDTO.setCreated(entity.getCreated());
        maintenanceDTO.setUpdated(entity.getUpdated());
        return maintenanceDTO;
    }

}
