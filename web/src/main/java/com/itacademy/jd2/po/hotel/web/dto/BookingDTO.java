package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.web.validator.TodayOrFuture;

public class BookingDTO implements Comparable<BookingDTO> {

    private Integer id;

    @NotNull
    private Integer roomId;

    private Integer roomNumber;

    @NotNull
    private Integer userAccountId;

    private String userAccountEmail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TodayOrFuture
    @NotNull
    private Date checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TodayOrFuture
    @NotNull
    private Date checkOut;

    private Integer period;

    // поле для таблицы chess
    private Integer colspan;

    @NotNull
    @Min(0)
    @Digits(integer = 10, fraction = 2) // валидация на 2 знака после запятой
    private Double roomPrice;

    @NotNull
    private Integer bookingStatusId;

    private String bookingStatusName;

    private String bookingStatusColor;

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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(final Integer period) {
        this.period = period;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(final Integer colspan) {
        this.colspan = colspan;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(final Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Integer getBookingStatusId() {
        return bookingStatusId;
    }

    public void setBookingStatusId(final Integer bookingStatusId) {
        this.bookingStatusId = bookingStatusId;
    }

    public String getBookingStatusName() {
        return bookingStatusName;
    }

    public void setBookingStatusName(final String bookingStatusName) {
        this.bookingStatusName = bookingStatusName;
    }

    public String getBookingStatusColor() {
        return bookingStatusColor;
    }

    public void setBookingStatusColor(final String bookingStatusColor) {
        this.bookingStatusColor = bookingStatusColor;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
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

    @Override
    public int compareTo(final BookingDTO booking) {
        return (int) (this.checkIn.getTime() - booking.checkIn.getTime());
    }
}
