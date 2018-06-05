package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;

@Entity
public class Task extends BaseEntity implements ITask {

    @Column
    private String toDo;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column
    private Date executionTime;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount answerable;

    @Column
    private Boolean executed;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount creator;

    @Column
    @Version
    private Integer version;

    @Override
    public String getToDo() {
        return toDo;
    }

    @Override
    public void setToDo(final String toDo) {
        this.toDo = toDo;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public TaskPriority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(final TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public Date getExecutionTime() {
        return executionTime;
    }

    @Override
    public void setExecutionTime(final Date executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public IUserAccount getAnswerable() {
        return answerable;
    }

    @Override
    public void setAnswerable(final IUserAccount answerable) {
        this.answerable = answerable;
    }

    @Override
    public Boolean getExecuted() {
        return executed;
    }

    @Override
    public void setExecuted(final Boolean executed) {
        this.executed = executed;
    }

    @Override
    public IUserAccount getCreator() {
        return creator;
    }

    @Override
    public void setCreator(final IUserAccount creator) {
        this.creator = creator;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(final Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Task [toDo=" + toDo + ", description=" + description + ", priority=" + priority + ", executionTime="
                + executionTime + ", executed=" + executed + ", getId()=" + getId() + "]";
    }
}
