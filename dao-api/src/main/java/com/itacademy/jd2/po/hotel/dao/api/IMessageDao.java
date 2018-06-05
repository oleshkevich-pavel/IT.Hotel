package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;

public interface IMessageDao extends IBaseDao<IMessage, Integer> {

    List<IMessage> find(MessageFilter filter);

    long getCount(MessageFilter filter);
}
