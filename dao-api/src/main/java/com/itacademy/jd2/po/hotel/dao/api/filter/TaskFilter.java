package com.itacademy.jd2.po.hotel.dao.api.filter;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;

public class TaskFilter extends AbstractFilter {

    private String toDo;

    private String description;

    private TaskPriority priority;

    private Date executionDate;

    private Integer answerableId;

    private Boolean executed;

    private Integer creatorId;

    private boolean fetchAnswerable;

    private boolean fetchCreator;

    public String getToDo() {
        return toDo;
    }

    public void setToDo(final String toDo) {
        this.toDo = toDo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(final TaskPriority priority) {
        this.priority = priority;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    public Integer getAnswerableId() {
        return answerableId;
    }

    public void setAnswerableId(final Integer answerableId) {
        this.answerableId = answerableId;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(final Boolean executed) {
        this.executed = executed;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(final Integer creatorId) {
        this.creatorId = creatorId;
    }

    public boolean getFetchAnswerable() {
        return fetchAnswerable;
    }

    public void setFetchAnswerable(final boolean fetchAnswerable) {
        this.fetchAnswerable = fetchAnswerable;
    }

    public boolean getFetchCreator() {
        return fetchCreator;
    }

    public void setFetchCreator(final boolean fetchCreator) {
        this.fetchCreator = fetchCreator;
    }

}
