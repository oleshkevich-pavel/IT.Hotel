package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.UserAccountDTO;

@Component
public class UserAccountToDTOConverter implements Function<IUserAccount, UserAccountDTO> {

    @Override
    public UserAccountDTO apply(final IUserAccount entity) {
        final UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(entity.getId());
        userAccountDTO.setEmail(entity.getEmail());
        userAccountDTO.setPassword(entity.getPassword());
        userAccountDTO.setRole(entity.getRole().name());
        userAccountDTO.setFirstName(entity.getFirstName());
        userAccountDTO.setLastName(entity.getLastName());
        userAccountDTO.setBirthday(entity.getBirthday());
        userAccountDTO.setAddress(entity.getAddress());
        userAccountDTO.setPhone(entity.getPhone());
        userAccountDTO.setCreated(entity.getCreated());
        userAccountDTO.setUpdated(entity.getUpdated());

        return userAccountDTO;
    }

}
