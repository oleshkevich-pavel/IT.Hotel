package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

public class EmployeeDTO {

    private Integer id;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date hiring;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date layoff;

    @NotNull
    private Integer postId;

    private String postName;

    private Date created;

    private Date updated;

    @NotNull
    @Valid
    private UserAccountDTO userAccount = new UserAccountDTO();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Date getHiring() {
        return hiring;
    }

    public void setHiring(final Date hiring) {
        this.hiring = hiring;
    }

    public Date getLayoff() {
        return layoff;
    }

    public void setLayoff(final Date layoff) {
        this.layoff = layoff;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(final Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(final String postName) {
        this.postName = postName;
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

    public UserAccountDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(final UserAccountDTO userAccount) {
        this.userAccount = userAccount;
    }
}
