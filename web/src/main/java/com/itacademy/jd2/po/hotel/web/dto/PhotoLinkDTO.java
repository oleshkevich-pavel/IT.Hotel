package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

public class PhotoLinkDTO {

    private Integer id;

    @NotNull
    private Integer roomId;

    private Integer roomNumber;

    @NotNull
    private Integer userAccountId;

    private String userAccountEmail;

    @Size(min = 1, max = 300)
    @URL
    private String link;

    private Date created;
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(final Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(final Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(final Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountEmail() {
        return userAccountEmail;
    }

    public void setUserAccountEmail(final String userAccountEmail) {
        this.userAccountEmail = userAccountEmail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
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
