package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.EmployeeDTO;

@Component
public class EmployeeToDTOConverter implements Function<IEmployee, EmployeeDTO> {

    @Override
    public EmployeeDTO apply(final IEmployee entity) {
        final EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(entity.getId());
        employeeDTO.setCreated(entity.getCreated());
        employeeDTO.setUpdated(entity.getUpdated());
        employeeDTO.setHiring(entity.getHiring());
        employeeDTO.setLayoff(entity.getLayoff());

        final IPost post = entity.getPost();
        if (post != null) {
            employeeDTO.setPostId(post.getId());
            employeeDTO.setPostName(post.getName());
        }

        final IUserAccount userAccount = entity.getUserAccount();
        if (userAccount != null) {
            employeeDTO.getUserAccount().setEmail(userAccount.getEmail());
            employeeDTO.getUserAccount().setPassword(userAccount.getPassword());
            employeeDTO.getUserAccount().setRole(userAccount.getRole().name());
            employeeDTO.getUserAccount().setFirstName(userAccount.getFirstName());
            employeeDTO.getUserAccount().setLastName(userAccount.getLastName());
            employeeDTO.getUserAccount().setBirthday(userAccount.getBirthday());
            employeeDTO.getUserAccount().setAddress(userAccount.getAddress());
            employeeDTO.getUserAccount().setPhone(userAccount.getPhone());
        }
        return employeeDTO;
    }
}
