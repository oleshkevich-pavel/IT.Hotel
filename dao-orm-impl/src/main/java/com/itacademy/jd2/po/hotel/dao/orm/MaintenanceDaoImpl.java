package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.orm.model.Maintenance;
import com.itacademy.jd2.po.hotel.dao.orm.model.Maintenance_;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<IMaintenance, Integer> implements IMaintenanceDao {

    protected MaintenanceDaoImpl() {
        super(Maintenance.class);
    }

    @Override
    public IMaintenance createEntity() {
        return new Maintenance();
    }

    @Override
    public List<IMaintenance> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMaintenance> cq = cb.createQuery(IMaintenance.class);
        final Root<Maintenance> from = cq.from(Maintenance.class);
        cq.select(from);
        final TypedQuery<IMaintenance> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IMaintenance getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMaintenance> cq = cb.createQuery(IMaintenance.class);
        final Root<Maintenance> from = cq.from(Maintenance.class);
        cq.select(from);

        cq.where(cb.equal(from.get(Maintenance_.id), id));

        final TypedQuery<IMaintenance> q = em.createQuery(cq);
        List<IMaintenance> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IMaintenance> find(final MaintenanceFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMaintenance> cq = cb.createQuery(IMaintenance.class);
        final Root<Maintenance> from = cq.from(Maintenance.class);
        cq.select(from);

        applyFilter(filter, cb, cq, from);
        // set sort params
        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IMaintenance> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final MaintenanceFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Maintenance> from = cq.from(Maintenance.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final MaintenanceFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Maintenance> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String name = filter.getName();
        if (StringUtils.isNotBlank(name)) {
            ands.add(cb.equal(from.get(Maintenance_.name), name));
        }
        final Boolean available = filter.getAvailable();
        if (Boolean.TRUE.equals(available)) {
            ands.add(cb.equal(from.get(Maintenance_.available), true));
        }
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Maintenance> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(Maintenance_.name);
        case "actualPrice":
            return from.get(Maintenance_.actualPrice);
        case "available":
            return from.get(Maintenance_.available);
        case "created":
            return from.get(Maintenance_.created);
        case "updated":
            return from.get(Maintenance_.updated);
        case "id":
            return from.get(Maintenance_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
