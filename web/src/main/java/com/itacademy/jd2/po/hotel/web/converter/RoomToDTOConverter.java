package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;

@Component
public class RoomToDTOConverter implements Function<IRoom, RoomDTO> {

    @Override
    public RoomDTO apply(final IRoom entity) {
        final RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(entity.getId());
        roomDTO.setNumber(entity.getNumber());
        roomDTO.setFloor(entity.getFloor());
        roomDTO.setType(entity.getType().name());
        roomDTO.setAccomodation(entity.getAccomodation().name());
        roomDTO.setView(entity.getView().name());
        roomDTO.setActualPrice(entity.getActualPrice());
        roomDTO.setDescription(entity.getDescription());
        roomDTO.setDirty(entity.isDirty());
        roomDTO.setBroken(entity.isBroken());
        roomDTO.setCreated(entity.getCreated());
        roomDTO.setUpdated(entity.getUpdated());
        return roomDTO;
    }

}
