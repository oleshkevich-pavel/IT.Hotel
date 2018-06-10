package com.itacademy.jd2.po.hotel.service;

import java.io.IOException;
import java.util.Locale;

import javax.transaction.Transactional;

public interface ILocaleService {

    Locale get(Integer userAccountId) throws Exception;

    @Transactional
    void save(Locale locale, Integer userAccountId) throws IOException;
}
