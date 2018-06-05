package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.GuestDTO;

@Component
public class GuestToDTOConverter implements Function<IGuest, GuestDTO> {

    @Override
    public GuestDTO apply(final IGuest entity) {
        final GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(entity.getId());
        guestDTO.setVerifyKey(entity.getVerifyKey());
        guestDTO.setVerified(entity.getVerified());
        guestDTO.setCredit(entity.getCredit());

        final IGuestStatus guestStatus = entity.getGuestStatus();
        if (guestStatus != null) {
            guestDTO.setGuestStatusId(guestStatus.getId());
            guestDTO.setGuestStatusName(guestStatus.getName());
        }
        final IUserAccount userAccount = entity.getUserAccount();

        if (userAccount != null) {
            guestDTO.getUserAccount().setEmail(userAccount.getEmail());
            guestDTO.getUserAccount().setPassword(userAccount.getPassword());
            guestDTO.getUserAccount().setRole(userAccount.getRole().name());
            guestDTO.getUserAccount().setFirstName(userAccount.getFirstName());
            guestDTO.getUserAccount().setLastName(userAccount.getLastName());
            guestDTO.getUserAccount().setBirthday(userAccount.getBirthday());
            guestDTO.getUserAccount().setAddress(userAccount.getAddress());
            guestDTO.getUserAccount().setPhone(userAccount.getPhone());
        }

        guestDTO.setCreated(entity.getCreated());
        guestDTO.setUpdated(entity.getUpdated());
        return guestDTO;
    }
}
