package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.UserAccountDTO;

@Component
public class UserAccountFromDTOConverter implements Function<UserAccountDTO, IUserAccount> {

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public IUserAccount apply(final UserAccountDTO dto) {
        final IUserAccount entity = userAccountService.createEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(Role.valueOf(dto.getRole()));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthday(dto.getBirthday());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());

        return entity;
    }
}
