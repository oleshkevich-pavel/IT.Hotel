package com.itacademy.jd2.po.hotel.dao.api.model.base;

import java.util.Date;

public interface IBaseEntity {

    Integer getId();

    void setId(Integer id);

    Date getCreated();

    void setCreated(Date created);

    Date getUpdated();

    void setUpdated(Date updated);

}
