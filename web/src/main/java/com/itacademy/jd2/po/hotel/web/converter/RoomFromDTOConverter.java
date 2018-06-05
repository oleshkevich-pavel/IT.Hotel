package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;

@Component
public class RoomFromDTOConverter implements Function<RoomDTO, IRoom> {

    @Autowired
    private IRoomService roomService;

    @Override
    public IRoom apply(final RoomDTO dto) {
        final IRoom entity = roomService.createEntity();
        entity.setId(dto.getId());
        entity.setNumber(dto.getNumber());
        entity.setFloor(dto.getFloor());
        entity.setType(RoomType.valueOf(dto.getType()));
        entity.setAccomodation(Accomodation.valueOf(dto.getAccomodation()));
        entity.setView(ViewType.valueOf(dto.getView()));
        entity.setActualPrice(dto.getActualPrice());
        entity.setDescription(dto.getDescription());
        entity.setDirty(dto.getDirty());
        entity.setBroken(dto.getBroken());

        return entity;
    }
}
