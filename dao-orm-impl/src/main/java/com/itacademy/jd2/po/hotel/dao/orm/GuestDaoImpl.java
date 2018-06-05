package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
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

import com.itacademy.jd2.po.hotel.dao.api.IGuestDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.orm.model.Guest;
import com.itacademy.jd2.po.hotel.dao.orm.model.GuestStatus_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Guest_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class GuestDaoImpl extends AbstractDaoImpl<IGuest, Integer> implements IGuestDao {

    protected GuestDaoImpl() {
        super(Guest.class);
    }

    @Override
    public IGuest createEntity() {
        return new Guest();
    }

    @Override
    public List<IGuest> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuest> cq = cb.createQuery(IGuest.class);
        final Root<Guest> from = cq.from(Guest.class);
        cq.select(from);

        from.fetch(Guest_.userAccount, JoinType.LEFT);
        from.fetch(Guest_.guestStatus, JoinType.LEFT);

        final TypedQuery<IGuest> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IGuest getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuest> cq = cb.createQuery(IGuest.class);
        final Root<Guest> from = cq.from(Guest.class);
        cq.select(from);

        from.fetch(Guest_.userAccount, JoinType.LEFT);
        from.fetch(Guest_.guestStatus, JoinType.LEFT);

        cq.where(cb.equal(from.get(Guest_.id), id));

        final TypedQuery<IGuest> q = em.createQuery(cq);
        List<IGuest> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IGuest> find(final GuestFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IGuest> cq = cb.createQuery(IGuest.class);
        final Root<Guest> from = cq.from(Guest.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(Guest_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchGuestStatus()) {
            from.fetch(Guest_.guestStatus, JoinType.LEFT);
        }

        applyFilter(filter, cb, cq, from);
        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IGuest> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final GuestFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Guest> from = cq.from(Guest.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final GuestFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Guest> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String verifyKey = filter.getVerifyKey();
        if (StringUtils.isNotBlank(verifyKey)) {
            ands.add(cb.equal(from.get(Guest_.verifyKey), verifyKey));
        }
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Guest> from, final String sortColumn) {
        switch (sortColumn) {
        case "verifyKey":
            return from.get(Guest_.verifyKey);
        case "verified":
            return from.get(Guest_.verified);
        case "email":
            return from.get(Guest_.userAccount).get(UserAccount_.email);
        case "password":
            return from.get(Guest_.userAccount).get(UserAccount_.password);
        case "role":
            return from.get(Guest_.userAccount).get(UserAccount_.role);
        case "firstName":
            return from.get(Guest_.userAccount).get(UserAccount_.firstName);
        case "lastName":
            return from.get(Guest_.userAccount).get(UserAccount_.lastName);
        case "birthday":
            return from.get(Guest_.userAccount).get(UserAccount_.birthday);
        case "address":
            return from.get(Guest_.userAccount).get(UserAccount_.address);
        case "phone":
            return from.get(Guest_.userAccount).get(UserAccount_.phone);
        case "guestStatus":
            return from.get(Guest_.guestStatus).get(GuestStatus_.name);
        case "credit":
            return from.get(Guest_.credit);
        case "created":
            return from.get(Guest_.created);
        case "updated":
            return from.get(Guest_.updated);
        case "id":
            return from.get(Guest_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }

}
