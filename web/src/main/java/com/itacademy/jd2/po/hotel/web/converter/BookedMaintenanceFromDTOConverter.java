package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IBookedMaintenanceService;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.BookedMaintenanceDTO;

@Component
public class BookedMaintenanceFromDTOConverter implements Function<BookedMaintenanceDTO, IBookedMaintenance> {

    @Autowired
    private IBookedMaintenanceService bookedMaintenanceService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IMaintenanceService maintenanceService;

    @Override
    public IBookedMaintenance apply(final BookedMaintenanceDTO dto) {
        final IBookedMaintenance entity = bookedMaintenanceService.createEntity();
        entity.setId(dto.getId());

        final IRoom room = roomService.createEntity();
        room.setId(dto.getRoomId());
        entity.setRoom(room);

        final IUserAccount userAccount = userAccountService.createEntity();
        userAccount.setId(dto.getUserAccountId());
        entity.setUserAccount(userAccount);

        final IMaintenance maintenance = maintenanceService.createEntity();
        maintenance.setId(dto.getMaintenanceId());
        entity.setMaintenance(maintenance);

        entity.setTime(dto.getTime());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
