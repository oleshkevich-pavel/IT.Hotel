package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.service.IPostService;
import com.itacademy.jd2.po.hotel.web.dto.PostDTO;

@Component
public class PostFromDTOConverter implements Function<PostDTO, IPost> {

    @Autowired
    private IPostService postService;

    @Override
    public IPost apply(final PostDTO dto) {
        final IPost entity = postService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        if (dto.getSupervisorId() == null) {
            entity.setSupervisor(null);
        } else {
            final IPost supervisor = postService.createEntity();
            supervisor.setId(dto.getSupervisorId());
            entity.setSupervisor(supervisor);
        }
        return entity;
    }
}
