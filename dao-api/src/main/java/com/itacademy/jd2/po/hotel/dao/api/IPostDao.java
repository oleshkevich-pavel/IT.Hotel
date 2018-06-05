package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;

public interface IPostDao extends IBaseDao<IPost, Integer> {

    List<IPost> find(PostFilter filter);

    long getCount(PostFilter filter);
}
