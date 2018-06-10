package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;

public interface IMessageService {

    IMessage createEntity();

    @Transactional
    void save(IMessage entity) throws MessagingException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IMessage get(Integer id);

    List<IMessage> getAll();

    List<IMessage> find(MessageFilter filter);

    long getCount(MessageFilter filter);

    List<IMessage> getAllFullInfo();

    void setEmailSenderService(IEmailSenderService emailSenderService);
}
