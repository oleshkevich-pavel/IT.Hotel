package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class UserAccountDaoImpl extends AbstractDaoImpl<IUserAccount, Integer> implements IUserAccountDao {

    protected UserAccountDaoImpl() {
        super(UserAccount.class);
    }

    @Override
    public IUserAccount createEntity() {
        return new UserAccount();
    }

    @Override
    public List<IUserAccount> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUserAccount> cq = cb.createQuery(IUserAccount.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);

        final TypedQuery<IUserAccount> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IUserAccount getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUserAccount> cq = cb.createQuery(IUserAccount.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);

        cq.where(cb.equal(from.get(UserAccount_.id), id));

        final TypedQuery<IUserAccount> q = em.createQuery(cq);
        List<IUserAccount> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public IUserAccount getByEmail(final String email) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUserAccount> cq = cb.createQuery(IUserAccount.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);

        cq.where(cb.equal(from.get(UserAccount_.email), email));

        final TypedQuery<IUserAccount> q = em.createQuery(cq);
        List<IUserAccount> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IUserAccount> find(final UserAccountFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUserAccount> cq = cb.createQuery(IUserAccount.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IUserAccount> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final UserAccountFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<UserAccount> from = cq.from(UserAccount.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<UserAccount> from, final String sortColumn) {
        switch (sortColumn) {
        case "email":
            return from.get(UserAccount_.email);
        case "password":
            return from.get(UserAccount_.password);
        case "role":
            return from.get(UserAccount_.role);
        case "firstName":
            return from.get(UserAccount_.firstName);
        case "lastName":
            return from.get(UserAccount_.lastName);
        case "birthday":
            return from.get(UserAccount_.birthday);
        case "address":
            return from.get(UserAccount_.address);
        case "phone":
            return from.get(UserAccount_.phone);
        case "created":
            return from.get(UserAccount_.created);
        case "updated":
            return from.get(UserAccount_.updated);
        case "id":
            return from.get(UserAccount_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
