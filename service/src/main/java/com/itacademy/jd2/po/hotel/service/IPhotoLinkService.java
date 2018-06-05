package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;

public interface IPhotoLinkService {

    IPhotoLink get(Integer id);

    @Transactional
    void save(IPhotoLink entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IPhotoLink createEntity();

    List<IPhotoLink> getAll();

    List<IPhotoLink> find(PhotoLinkFilter filter);

    long getCount(PhotoLinkFilter filter);

    IPhotoLink getFullInfo(Integer id);

    List<IPhotoLink> getAllFullInfo();
}
