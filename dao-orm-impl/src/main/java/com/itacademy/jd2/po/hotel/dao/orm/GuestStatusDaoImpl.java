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
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IGuestStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.orm.model.GuestStatus;
import com.itacademy.jd2.po.hotel.dao.orm.model.GuestStatus_;

@Repository
public class GuestStatusDaoImpl extends AbstractDaoImpl<IGuestStatus, Integer> implements IGuestStatusDao {

    protected GuestStatusDaoImpl() {
        super(GuestStatus.class);
    }

    @Override
    public IGuestStatus createEntity() {
        return new GuestStatus();
    }

    @Override
    public List<IGuestStatus> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuestStatus> cq = cb.createQuery(IGuestStatus.class);
        final Root<GuestStatus> from = cq.from(GuestStatus.class);
        cq.select(from);
        final TypedQuery<IGuestStatus> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IGuestStatus getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuestStatus> cq = cb.createQuery(IGuestStatus.class);
        final Root<GuestStatus> from = cq.from(GuestStatus.class);
        cq.select(from);

        cq.where(cb.equal(from.get(GuestStatus_.id), id));

        final TypedQuery<IGuestStatus> q = em.createQuery(cq);
        List<IGuestStatus> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IGuestStatus> find(final GuestStatusFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuestStatus> cq = cb.createQuery(IGuestStatus.class);
        final Root<GuestStatus> from = cq.from(GuestStatus.class);
        cq.select(from);

        applyFilter(filter, cb, cq, from);

        if (filter.getSortColumn() != null) {
            final SingularAttribute<? super GuestStatus, ?> sortProperty = toMetamodelFormat(filter.getSortColumn());
            final Path<?> expression = from.get(sortProperty);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IGuestStatus> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final GuestStatusFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<GuestStatus> from = cq.from(GuestStatus.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final GuestStatusFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<GuestStatus> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String name = filter.getName();
        if (StringUtils.isNotBlank(name)) {
            ands.add(cb.equal(from.get(GuestStatus_.name), name));
        }
        
        final Integer discount = filter.getDiscount();
        if (discount != null) {
            ands.add(cb.equal(from.get(GuestStatus_.discount), discount));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    @Override
    public Integer getMinDiscount() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        final Root<GuestStatus> from = cq.from(GuestStatus.class);
        cq.select(cb.min(from.get(GuestStatus_.discount)));

        final TypedQuery<Integer> q = em.createQuery(cq);
        List<Integer> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    private SingularAttribute<? super GuestStatus, ?> toMetamodelFormat(final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return GuestStatus_.name;
        case "discount":
            return GuestStatus_.discount;
        case "created":
            return GuestStatus_.created;
        case "updated":
            return GuestStatus_.updated;
        case "id":
            return GuestStatus_.id;
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
