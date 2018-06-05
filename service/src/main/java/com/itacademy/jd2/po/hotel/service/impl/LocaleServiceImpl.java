package com.itacademy.jd2.po.hotel.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IUnstructuredObjectDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.ILocaleService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;

@Service
public class LocaleServiceImpl implements ILocaleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleServiceImpl.class);

    @Autowired
    private IUnstructuredObjectDao dao;
    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public Locale get(final Integer userAccountId) {
        Locale locale = null;
        List<IUnstructuredObject> listEntity = dao.find(getLocaleFilter(userAccountId));
        if (listEntity.size() != 0) {
            byte[] arrayByte = listEntity.get(0).getContent();
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(arrayByte);
                ObjectInputStream in = new ObjectInputStream(bis);
                locale = (Locale) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
            }
        }
        return locale;
    }

    @Override
    public void save(final Locale locale, final Integer userAccountId) {
        IUnstructuredObject entity = dao.createEntity();
        List<IUnstructuredObject> listEntity = dao.find(getLocaleFilter(userAccountId));
        if (listEntity.size() != 0) {
            entity = listEntity.get(0);
        }
        Date modifiedOn = new Date();
        entity.setUpdated(modifiedOn);
        applyEntity(locale, userAccountId, entity);
        if (entity.getId() == null) {
            entity.setCreated(modifiedOn);
            LOGGER.info("new saved locale {} for userAccount: {}", locale, userAccountId);
            dao.insert(entity);
        } else {
            LOGGER.info("updated locale {} for userAccount: {}", locale, userAccountId);
            dao.update(entity);
        }
    }

    private UnstructuredObjectFilter getLocaleFilter(final Integer userAccountId) {
        UnstructuredObjectFilter filter = new UnstructuredObjectFilter();
        filter.setUserAccountId(userAccountId);
        filter.setName("locale");

        return filter;
    }

    private IUnstructuredObject applyEntity(final Locale locale, final Integer userAccountId,
            final IUnstructuredObject entity) {
        entity.setName("locale");
        final IUserAccount userAccount = userAccountService.createEntity();
        userAccount.setId(userAccountId);
        entity.setUserAccount(userAccount);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(locale);
            entity.setContent(bos.toByteArray());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

}
