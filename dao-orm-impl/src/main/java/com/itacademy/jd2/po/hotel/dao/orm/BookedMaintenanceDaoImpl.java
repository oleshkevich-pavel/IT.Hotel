package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IBookedMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.orm.model.BookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.orm.model.BookedMaintenance_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Maintenance_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class BookedMaintenanceDaoImpl extends AbstractDaoImpl<IBookedMaintenance, Integer>
        implements IBookedMaintenanceDao {

    protected BookedMaintenanceDaoImpl() {
        super(BookedMaintenance.class);
    }

    @Override
    public IBookedMaintenance createEntity() {
        return new BookedMaintenance();
    }

    @Override
    public List<IBookedMaintenance> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookedMaintenance> cq = cb.createQuery(IBookedMaintenance.class);
        final Root<BookedMaintenance> from = cq.from(BookedMaintenance.class);
        cq.select(from);

        from.fetch(BookedMaintenance_.userAccount, JoinType.LEFT);
        from.fetch(BookedMaintenance_.maintenance, JoinType.LEFT);

        final TypedQuery<IBookedMaintenance> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IBookedMaintenance getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookedMaintenance> cq = cb.createQuery(IBookedMaintenance.class);
        final Root<BookedMaintenance> from = cq.from(BookedMaintenance.class);
        cq.select(from);

        from.fetch(BookedMaintenance_.userAccount, JoinType.LEFT);
        from.fetch(BookedMaintenance_.maintenance, JoinType.LEFT);

        cq.where(cb.equal(from.get(BookedMaintenance_.id), id));

        final TypedQuery<IBookedMaintenance> q = em.createQuery(cq);
        List<IBookedMaintenance> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IBookedMaintenance> find(final BookedMaintenanceFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBookedMaintenance> cq = cb.createQuery(IBookedMaintenance.class);
        final Root<BookedMaintenance> from = cq.from(BookedMaintenance.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(BookedMaintenance_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchMaintenance()) {
            from.fetch(BookedMaintenance_.maintenance, JoinType.LEFT);
        }
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IBookedMaintenance> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final BookedMaintenanceFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<BookedMaintenance> from = cq.from(BookedMaintenance.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public Double getMaxPrice() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        final Root<BookedMaintenance> from = cq.from(BookedMaintenance.class);
        cq.select(cb.max(from.get(BookedMaintenance_.price)));

        final TypedQuery<Double> q = em.createQuery(cq);
        List<Double> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    private void applyFilter(final BookedMaintenanceFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<BookedMaintenance> from) {
        final List<Predicate> ands = new ArrayList<>();

        final Integer userAccountId = filter.getUserAccountId();
        if (userAccountId != null) {
            ands.add(cb.equal(from.get(BookedMaintenance_.userAccount).get(UserAccount_.id), userAccountId));
        }

        final String userAccountEmail = filter.getUserAccountEmail();
        if (StringUtils.isNotBlank(userAccountEmail)) {
            ands.add(cb.equal(from.get(BookedMaintenance_.userAccount).get(UserAccount_.email), userAccountEmail));
        }

        final String maintenanceName = filter.getMaintenanceName();
        if (StringUtils.isNotBlank(maintenanceName)) {
            ands.add(cb.equal(from.get(BookedMaintenance_.maintenance).get(Maintenance_.name), maintenanceName));
        }

        final Date startTime = filter.getStartTime();
        final Date endTime = filter.getEndTime();

        if (startTime != null) {
            if (endTime != null) {
                ands.add(cb.between(from.get(BookedMaintenance_.time), startTime, endTime));
            } else {
                ands.add(cb.greaterThanOrEqualTo(from.get(BookedMaintenance_.time), startTime));
            }
        } else if (endTime != null) {
            ands.add(cb.lessThanOrEqualTo(from.get(BookedMaintenance_.time), endTime));
        }

        final Double priceMin = filter.getPriceMin();
        final Double priceMax = filter.getPriceMax();
        if (priceMin != null) {
            if (priceMax != null) {
                ands.add(cb.between(from.get(BookedMaintenance_.price), priceMin, priceMax));
            } else {
                ands.add(cb.greaterThanOrEqualTo(from.get(BookedMaintenance_.price), priceMin));
            }
        } else if (priceMax != null) {
            ands.add(cb.lessThanOrEqualTo(from.get(BookedMaintenance_.price), priceMax));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<BookedMaintenance> from, final String sortColumn) {
        switch (sortColumn) {
        case "userAccount":
            return from.get(BookedMaintenance_.userAccount).get(UserAccount_.email);
        case "maintenance":
            return from.get(BookedMaintenance_.maintenance).get(Maintenance_.name);
        case "time":
            return from.get(BookedMaintenance_.time);
        case "price":
            return from.get(BookedMaintenance_.price);
        case "created":
            return from.get(BookedMaintenance_.created);
        case "updated":
            return from.get(BookedMaintenance_.updated);
        case "id":
            return from.get(BookedMaintenance_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
