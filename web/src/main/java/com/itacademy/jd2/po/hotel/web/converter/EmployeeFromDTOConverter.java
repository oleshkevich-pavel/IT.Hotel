package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IEmployeeService;
import com.itacademy.jd2.po.hotel.service.IPostService;
import com.itacademy.jd2.po.hotel.web.dto.EmployeeDTO;

@Component
public class EmployeeFromDTOConverter implements Function<EmployeeDTO, IEmployee> {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPostService postService;

    @Override
    public IEmployee apply(final EmployeeDTO dto) {
        final IEmployee entity = employeeService.createEntity();
        entity.setId(dto.getId());

        final IUserAccount userAccount = employeeService.createUserAccountEntity();
        userAccount.setId(dto.getId());
        userAccount.setEmail(dto.getUserAccount().getEmail());
        userAccount.setPassword(dto.getUserAccount().getPassword());
        userAccount.setRole(Role.valueOf(dto.getUserAccount().getRole()));
        userAccount.setFirstName(dto.getUserAccount().getFirstName());
        userAccount.setLastName(dto.getUserAccount().getLastName());
        userAccount.setBirthday(dto.getUserAccount().getBirthday());
        userAccount.setAddress(dto.getUserAccount().getAddress());
        userAccount.setPhone(dto.getUserAccount().getPhone());
        entity.setUserAccount(userAccount);

        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setHiring(dto.getHiring());
        entity.setLayoff(dto.getLayoff());

        final IPost post = postService.createEntity();
        post.setId(dto.getPostId());
        entity.setPost(post);

        return entity;
    }
}
