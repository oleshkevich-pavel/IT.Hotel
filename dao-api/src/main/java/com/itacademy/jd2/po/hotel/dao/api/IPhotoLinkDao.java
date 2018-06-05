package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;

public interface IPhotoLinkDao extends IBaseDao<IPhotoLink, Integer> {

    List<IPhotoLink> find(PhotoLinkFilter filter);

    long getCount(PhotoLinkFilter filter);
}
