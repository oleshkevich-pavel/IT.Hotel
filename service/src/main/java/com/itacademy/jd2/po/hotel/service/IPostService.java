package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;

public interface IPostService {

    IPost get(Integer id);

    List<IPost> getAll();

    @Transactional
    void save(IPost entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IPost createEntity();

    List<IPost> find(PostFilter filter);

    long getCount(PostFilter filter);

    IPost getFullInfo(Integer id);

    List<IPost> getAllFullInfo();
}
