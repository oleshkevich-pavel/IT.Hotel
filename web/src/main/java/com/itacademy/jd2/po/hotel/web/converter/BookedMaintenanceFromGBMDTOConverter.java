package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.web.dto.BookedMaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.dto.GuestBookedMaintenanceDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Component
public class BookedMaintenanceFromGBMDTOConverter implements Function<GuestBookedMaintenanceDTO, IBookedMaintenance> {

    @Autowired
    private IMaintenanceService maintenanceService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private BookedMaintenanceFromDTOConverter bookedMaintenanceFromDTOConverter;

    @Override
    public IBookedMaintenance apply(final GuestBookedMaintenanceDTO guestBookedMaintenanceDTO) {
        BookedMaintenanceDTO dto = new BookedMaintenanceDTO();
        dto.setMaintenanceId(guestBookedMaintenanceDTO.getMaintenanceId());
        dto.setDate(guestBookedMaintenanceDTO.getDate());
        dto.setTime(guestBookedMaintenanceDTO.getTime());
        dto.setUserAccountId(AuthHelper.getLoggedUserId());

        double discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
        Integer maintenanceId = guestBookedMaintenanceDTO.getMaintenanceId();
        IMaintenance maintenance = maintenanceService.get(maintenanceId);
        dto.setPrice(maintenance.getActualPrice() * (1 - discount / 100));

        final IBookedMaintenance entity = bookedMaintenanceFromDTOConverter.apply(dto);
        return entity;
    }
}
