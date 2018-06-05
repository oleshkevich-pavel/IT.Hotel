package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;
import com.itacademy.jd2.po.hotel.web.dto.GuestDTO;

@Component
public class GuestFromDTOConverter implements Function<GuestDTO, IGuest> {

    @Autowired
    private IGuestService guestService;
    @Autowired
    private IGuestStatusService guestStatusService;

    @Override
    public IGuest apply(final GuestDTO dto) {
        final IGuest entity = guestService.createEntity();
        entity.setId(dto.getId());
        entity.setVerifyKey(dto.getVerifyKey());
        entity.setVerified(dto.getVerified());

        final IGuestStatus guestStatus = guestStatusService.createEntity();
        guestStatus.setId(dto.getGuestStatusId());
        entity.setGuestStatus(guestStatus);

        final IUserAccount userAccount = guestService.createUserAccountEntity();
        userAccount.setId(dto.getId());
        userAccount.setEmail(dto.getUserAccount().getEmail());
        userAccount.setPassword(dto.getUserAccount().getPassword());
        String role = dto.getUserAccount().getRole();
        if (role != null) {
            userAccount.setRole(Role.valueOf(role));
        }
        userAccount.setFirstName(dto.getUserAccount().getFirstName());
        userAccount.setLastName(dto.getUserAccount().getLastName());
        userAccount.setBirthday(dto.getUserAccount().getBirthday());
        userAccount.setAddress(dto.getUserAccount().getAddress());
        userAccount.setPhone(dto.getUserAccount().getPhone());
        entity.setUserAccount(userAccount);
        entity.setCredit(dto.getCredit());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        return entity;
    }

}
