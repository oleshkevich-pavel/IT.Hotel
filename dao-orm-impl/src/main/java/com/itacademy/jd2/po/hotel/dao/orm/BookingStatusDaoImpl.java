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

import com.itacademy.jd2.po.hotel.dao.api.IBookingStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.orm.model.BookingStatus;
import com.itacademy.jd2.po.hotel.dao.orm.model.BookingStatus_;

@Repository
public class BookingStatusDaoImpl extends AbstractDaoImpl<IBookingStatus, Integer> implements IBookingStatusDao {

    protected BookingStatusDaoImpl() {
        super(BookingStatus.class);
    }

    @Override
    public IBookingStatus createEntity() {
        return new BookingStatus();
    }

    @Override
    public List<IBookingStatus> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookingStatus> cq = cb.createQuery(IBookingStatus.class);
        final Root<BookingStatus> from = cq.from(BookingStatus.class);
        cq.select(from);
        final TypedQuery<IBookingStatus> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IBookingStatus getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookingStatus> cq = cb.createQuery(IBookingStatus.class);
        final Root<BookingStatus> from = cq.from(BookingStatus.class);
        cq.select(from);

        cq.where(cb.equal(from.get(BookingStatus_.id), id));

        final TypedQuery<IBookingStatus> q = em.createQuery(cq);
        List<IBookingStatus> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IBookingStatus> find(final BookingStatusFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookingStatus> cq = cb.createQuery(IBookingStatus.class);
        final Root<BookingStatus> from = cq.from(BookingStatus.class);
        cq.select(from);

        applyFilter(filter, cb, cq, from);

        if (filter.getSortColumn() != null) {
            final SingularAttribute<? super BookingStatus, ?> sortProperty = toMetamodelFormat(filter.getSortColumn());
            final Path<?> expression = from.get(sortProperty);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IBookingStatus> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final BookingStatusFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<BookingStatus> from = cq.from(BookingStatus.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final BookingStatusFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<BookingStatus> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String name = filter.getName();
        if (StringUtils.isNotBlank(name)) {
            ands.add(cb.equal(from.get(BookingStatus_.name), name));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private SingularAttribute<? super BookingStatus, ?> toMetamodelFormat(final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return BookingStatus_.name;
        case "created":
            return BookingStatus_.created;
        case "updated":
            return BookingStatus_.updated;
        case "id":
            return BookingStatus_.id;
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
