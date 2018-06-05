package com.itacademy.jd2.po.hotel.dao.api.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;

public interface ITask extends IBaseEntity {

    int DEFAULT_VERSION = 1;

    String getToDo();

    void setToDo(String toDo);

    String getDescription();

    void setDescription(String description);

    TaskPriority getPriority();

    void setPriority(TaskPriority priority);

    Date getExecutionTime();

    void setExecutionTime(Date date);

    IUserAccount getAnswerable();

    void setAnswerable(IUserAccount answerable);

    Boolean getExecuted();

    void setExecuted(Boolean executed);

    IUserAccount getCreator();

    void setCreator(IUserAccount creator);

    Integer getVersion();

    void setVersion(Integer version);
}
