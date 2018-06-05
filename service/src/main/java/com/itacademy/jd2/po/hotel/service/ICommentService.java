package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;

public interface ICommentService {

    IComment get(Integer id);

    @Transactional
    void save(IComment entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IComment createEntity();

    List<IComment> getAll();

    List<IComment> find(CommentFilter filter);

    long getCount(CommentFilter filter);

    IComment getFullInfo(Integer id);

    List<IComment> getAllFullInfo();
}
