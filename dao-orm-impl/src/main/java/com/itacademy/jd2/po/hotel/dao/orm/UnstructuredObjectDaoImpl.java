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

import com.itacademy.jd2.po.hotel.dao.api.IUnstructuredObjectDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.orm.model.UnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.orm.model.UnstructuredObject_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class UnstructuredObjectDaoImpl extends AbstractDaoImpl<IUnstructuredObject, Integer>
        implements IUnstructuredObjectDao {

    protected UnstructuredObjectDaoImpl() {
        super(UnstructuredObject.class);
    }

    @Override
    public IUnstructuredObject createEntity() {
        return new UnstructuredObject();
    }

    @Override
    public List<IUnstructuredObject> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUnstructuredObject> cq = cb.createQuery(IUnstructuredObject.class);
        final Root<UnstructuredObject> from = cq.from(UnstructuredObject.class);
        cq.select(from);

        from.fetch(UnstructuredObject_.userAccount, JoinType.LEFT);
        
        final TypedQuery<IUnstructuredObject> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IUnstructuredObject getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUnstructuredObject> cq = cb.createQuery(IUnstructuredObject.class);
        final Root<UnstructuredObject> from = cq.from(UnstructuredObject.class);
        cq.select(from);

        from.fetch(UnstructuredObject_.userAccount, JoinType.LEFT);
        
        cq.where(cb.equal(from.get(UnstructuredObject_.id), id));

        final TypedQuery<IUnstructuredObject> q = em.createQuery(cq);
        List<IUnstructuredObject> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IUnstructuredObject> find(final UnstructuredObjectFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IUnstructuredObject> cq = cb.createQuery(IUnstructuredObject.class);
        final Root<UnstructuredObject> from = cq.from(UnstructuredObject.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(UnstructuredObject_.userAccount, JoinType.LEFT);
        }

        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IUnstructuredObject> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final UnstructuredObjectFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<UnstructuredObject> from = cq.from(UnstructuredObject.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final UnstructuredObjectFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<UnstructuredObject> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String name = filter.getName();
        if (StringUtils.isNotBlank(name)) {
            ands.add(cb.equal(from.get(UnstructuredObject_.name), name));
        }

        final Integer userAccountId = filter.getUserAccountId();
        if (userAccountId != null) {
            ands.add(cb.equal(from.get(UnstructuredObject_.userAccount).get(UserAccount_.id), userAccountId));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<UnstructuredObject> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(UnstructuredObject_.name);
        case "content":
            return from.get(UnstructuredObject_.content);
        case "userAccount":
            return from.get(UnstructuredObject_.userAccount).get(UserAccount_.email);
        case "created":
            return from.get(UnstructuredObject_.created);
        case "updated":
            return from.get(UnstructuredObject_.updated);
        case "id":
            return from.get(UnstructuredObject_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
