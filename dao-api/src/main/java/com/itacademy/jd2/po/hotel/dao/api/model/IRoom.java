package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;

public interface IRoom extends IBaseEntity {

    Integer getNumber();

    void setNumber(Integer number);

    Integer getFloor();

    void setFloor(Integer floor);

    RoomType getType();

    void setType(RoomType type);

    Accomodation getAccomodation();

    void setAccomodation(Accomodation accomodation);

    ViewType getView();

    void setView(ViewType view);

    Double getActualPrice();

    void setActualPrice(Double actualPrice);

    String getDescription();

    void setDescription(String description);

    Boolean isDirty();

    void setDirty(Boolean dirty);

    Boolean isBroken();

    void setBroken(Boolean broken);
}
