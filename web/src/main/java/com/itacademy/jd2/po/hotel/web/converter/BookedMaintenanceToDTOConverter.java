package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.BookedMaintenanceDTO;

@Component
public class BookedMaintenanceToDTOConverter implements Function<IBookedMaintenance, BookedMaintenanceDTO> {

    @Override
    public BookedMaintenanceDTO apply(final IBookedMaintenance entity) {
        final BookedMaintenanceDTO bookedMaintenanceDTO = new BookedMaintenanceDTO();
        bookedMaintenanceDTO.setId(entity.getId());

        IUserAccount userAccount = entity.getUserAccount();
        if (userAccount != null) {
            bookedMaintenanceDTO.setUserAccountId(userAccount.getId());
            bookedMaintenanceDTO.setUserAccountEmail(userAccount.getEmail());
        }

        IMaintenance maintenance = entity.getMaintenance();
        if (maintenance != null) {
            bookedMaintenanceDTO.setMaintenanceId(maintenance.getId());
            bookedMaintenanceDTO.setMaintenanceName(maintenance.getName());
        }

        bookedMaintenanceDTO.setTime(entity.getTime());
        bookedMaintenanceDTO.setPrice(entity.getPrice());
        bookedMaintenanceDTO.setCreated(entity.getCreated());
        bookedMaintenanceDTO.setUpdated(entity.getUpdated());

        return bookedMaintenanceDTO;
    }

}
