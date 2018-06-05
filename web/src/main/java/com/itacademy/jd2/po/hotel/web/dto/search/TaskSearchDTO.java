package com.itacademy.jd2.po.hotel.web.dto.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;

public class TaskSearchDTO {

    private String toDo;

    private String description;

    private TaskPriority priority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date executionDate;

    private Integer answerableId;

    private String answerableEmail;

    private Boolean executed;

    private Integer creatorId;

    private String creatorEmail;

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

    public String getAnswerableEmail() {
        return answerableEmail;
    }

    public void setAnswerableEmail(final String answerableEmail) {
        this.answerableEmail = answerableEmail;
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

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(final String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }
}
