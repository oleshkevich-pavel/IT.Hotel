package com.itacademy.jd2.po.hotel.service.impl;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itacademy.jd2.po.hotel.service.IHibernateSearchService;

@Configuration
public class HibernateSearchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSearchServiceImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Bean
    IHibernateSearchService hibernateSearchService() throws InterruptedException {
        final IHibernateSearchService hibernateSearchService = new HibernateSearchServiceImpl(entityManager);
        try {
            hibernateSearchService.initializeHibernateSearch();
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
        return hibernateSearchService;
    }
}
