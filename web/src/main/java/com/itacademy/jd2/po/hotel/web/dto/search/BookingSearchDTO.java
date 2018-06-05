package com.itacademy.jd2.po.hotel.web.dto.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;

public class BookingSearchDTO {

    private Integer roomNumberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOut;

    private Integer userAccountId;

    private Integer bookingStatusId;

    private Accomodation accomodation;

    private ViewType view;

    private Double priceMin;

    private Double priceMax;

    public Integer getRoomNumberId() {
        return roomNumberId;
    }

    public void setRoomNumberId(final Integer roomNumberId) {
        this.roomNumberId = roomNumberId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(final Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(final Date checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(final Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Integer getBookingStatusId() {
        return bookingStatusId;
    }

    public void setBookingStatusId(final Integer bookingStatusId) {
        this.bookingStatusId = bookingStatusId;
    }

    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(final Accomodation accomodation) {
        this.accomodation = accomodation;
    }

    public ViewType getView() {
        return view;
    }

    public void setView(final ViewType view) {
        this.view = view;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(final Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(final Double priceMax) {
        this.priceMax = priceMax;
    }
}
