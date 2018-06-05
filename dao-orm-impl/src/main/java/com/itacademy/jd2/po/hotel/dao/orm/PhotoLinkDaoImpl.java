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

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IPhotoLinkDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.orm.model.PhotoLink;
import com.itacademy.jd2.po.hotel.dao.orm.model.PhotoLink_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Room_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class PhotoLinkDaoImpl extends AbstractDaoImpl<IPhotoLink, Integer> implements IPhotoLinkDao {

    protected PhotoLinkDaoImpl() {
        super(PhotoLink.class);
    }

    @Override
    public IPhotoLink createEntity() {
        return new PhotoLink();
    }

    @Override
    public List<IPhotoLink> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IPhotoLink> cq = cb.createQuery(IPhotoLink.class);
        final Root<PhotoLink> from = cq.from(PhotoLink.class);
        cq.select(from);

        from.fetch(PhotoLink_.userAccount, JoinType.LEFT);
        from.fetch(PhotoLink_.room, JoinType.LEFT);

        final TypedQuery<IPhotoLink> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IPhotoLink getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IPhotoLink> cq = cb.createQuery(IPhotoLink.class);
        final Root<PhotoLink> from = cq.from(PhotoLink.class);
        cq.select(from);

        from.fetch(PhotoLink_.userAccount, JoinType.LEFT);
        from.fetch(PhotoLink_.room, JoinType.LEFT);

        cq.where(cb.equal(from.get(PhotoLink_.id), id));

        final TypedQuery<IPhotoLink> q = em.createQuery(cq);
        List<IPhotoLink> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IPhotoLink> find(final PhotoLinkFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IPhotoLink> cq = cb.createQuery(IPhotoLink.class);
        final Root<PhotoLink> from = cq.from(PhotoLink.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(PhotoLink_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchRoom()) {
            from.fetch(PhotoLink_.room, JoinType.LEFT);
        }
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IPhotoLink> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final PhotoLinkFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<PhotoLink> from = cq.from(PhotoLink.class);
        cq.select(cb.count(from));

        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final PhotoLinkFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<PhotoLink> from) {

        final List<Predicate> ands = new ArrayList<>();

        final Integer roomId = filter.getRoomId();
        if (roomId != null) {
            ands.add(cb.equal(from.get(PhotoLink_.room).get(Room_.id), roomId));
        }

        final Integer roomNumber = filter.getRoomNumber();
        if (roomNumber != null) {
            ands.add(cb.equal(from.get(PhotoLink_.room).get(Room_.number), roomNumber));
        }

        final Integer userAccountId = filter.getUserAccountId();
        if (userAccountId != null) {
            ands.add(cb.equal(from.get(PhotoLink_.userAccount).get(UserAccount_.id), userAccountId));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<PhotoLink> from, final String sortColumn) {
        switch (sortColumn) {
        case "room":
            return from.get(PhotoLink_.room).get(Room_.number);
        case "userAccount":
            return from.get(PhotoLink_.userAccount).get(UserAccount_.email);
        case "link":
            return from.get(PhotoLink_.link);
        case "created":
            return from.get(PhotoLink_.created);
        case "updated":
            return from.get(PhotoLink_.updated);
        case "id":
            return from.get(PhotoLink_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
