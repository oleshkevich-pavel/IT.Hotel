package com.itacademy.jd2.po.hotel.service;

import javax.mail.MessagingException;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public interface IEmailSenderService {

    void sendEmailFromWebSite(IMessage entity) throws MessagingException;

    void sendVerifyKey(IUserAccount userAccount, String verifyKey) throws MessagingException;
}
