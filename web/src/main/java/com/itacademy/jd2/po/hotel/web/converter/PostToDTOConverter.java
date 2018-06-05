package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.web.dto.PostDTO;

@Component
public class PostToDTOConverter implements Function<IPost, PostDTO> {

    @Override
    public PostDTO apply(final IPost entity) {
        final PostDTO postDTO = new PostDTO();
        postDTO.setId(entity.getId());
        postDTO.setName(entity.getName());
        postDTO.setDescription(entity.getDescription());

        final IPost supervisor = entity.getSupervisor();
        if (supervisor != null) {
            postDTO.setSupervisorId(supervisor.getId());
            postDTO.setSupervisorName(supervisor.getName());
        }

        postDTO.setCreated(entity.getCreated());
        postDTO.setUpdated(entity.getUpdated());
        return postDTO;
    }

}
