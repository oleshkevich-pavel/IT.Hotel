package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskDTO {

    private Integer id;

    @Size(min = 1, max = 50) // should be the same as in DB constraints
    private String toDo;

    @Size(min = 1, max = 500)
    private String description;

    // @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date executionDate;

    @DateTimeFormat(pattern = "hh:mm a")
    private Date executionTime;

    @NotNull
    private Integer answerableId;

    private String answerableEmail;

    @Size(min = 1, max = 50)
    private String priority;

    @NotNull
    private Integer creatorId;

    private String creatorEmail;

    @NotNull
    private Boolean executed;

    @NotNull
    private Integer version;

    private Date created;
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

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

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(final Date executionTime) {
        this.executionTime = executionTime;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(final Boolean executed) {
        this.executed = executed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

}
