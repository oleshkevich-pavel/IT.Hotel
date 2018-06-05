package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;

public interface ICommentDao extends IBaseDao<IComment, Integer> {

    List<IComment> find(CommentFilter filter);

    long getCount(CommentFilter filter);
}
