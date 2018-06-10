package com.itacademy.jd2.po.hotel.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itacademy.jd2.po.hotel.service.IHibernateSearchService;

@Service
public class HibernateSearchServiceImpl<T> implements IHibernateSearchService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSearchServiceImpl.class);

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public HibernateSearchServiceImpl(final EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public void initializeHibernateSearch() throws InterruptedException {
        try {
            final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (final InterruptedException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<T> fuzzySearch(Class<?> T, final String field, final String searchTerm) {
        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(T).get();
        final Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields(field)
                .matching(searchTerm).createQuery();

        final javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, T);
        // execute search
        List<T> list = null;
        try {
            list = jpaQuery.getResultList();
        } catch (final NoResultException e) {
            LOGGER.info(e.getMessage());// do nothing
        }
        return list;
    }
}
