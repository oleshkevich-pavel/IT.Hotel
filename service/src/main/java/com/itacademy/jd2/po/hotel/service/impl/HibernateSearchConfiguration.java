package com.itacademy.jd2.po.hotel.service.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itacademy.jd2.po.hotel.service.IHibernateSearchService;

@Configuration
public class HibernateSearchConfiguration {

    @Autowired
    private EntityManager entityManager;

    @Bean
    IHibernateSearchService hibernateSearchService() {
        final IHibernateSearchService hibernateSearchService = new HibernateSearchServiceImpl(entityManager);
        hibernateSearchService.initializeHibernateSearch();
        return hibernateSearchService;
    }
}