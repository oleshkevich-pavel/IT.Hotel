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

import com.itacademy.jd2.po.hotel.dao.api.ICommentDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.orm.model.Comment;
import com.itacademy.jd2.po.hotel.dao.orm.model.Comment_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Room_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class CommentDaoImpl extends AbstractDaoImpl<IComment, Integer> implements ICommentDao {

    protected CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public IComment createEntity() {
        return new Comment();
    }

    @Override
    public List<IComment> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IComment> cq = cb.createQuery(IComment.class);
        final Root<Comment> from = cq.from(Comment.class);
        cq.select(from);

        from.fetch(Comment_.userAccount, JoinType.LEFT);
        from.fetch(Comment_.room, JoinType.LEFT);

        final TypedQuery<IComment> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IComment getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IComment> cq = cb.createQuery(IComment.class);
        final Root<Comment> from = cq.from(Comment.class);
        cq.select(from);

        from.fetch(Comment_.userAccount, JoinType.LEFT);
        from.fetch(Comment_.room, JoinType.LEFT);

        cq.where(cb.equal(from.get(Comment_.id), id));

        final TypedQuery<IComment> q = em.createQuery(cq);
        List<IComment> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IComment> find(final CommentFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IComment> cq = cb.createQuery(IComment.class);
        final Root<Comment> from = cq.from(Comment.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(Comment_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchRoom()) {
            from.fetch(Comment_.room, JoinType.LEFT);
        }
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IComment> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final CommentFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Comment> from = cq.from(Comment.class);
        cq.select(cb.count(from));

        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final CommentFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Comment> from) {
        final List<Predicate> ands = new ArrayList<>();

        final Integer roomId = filter.getRoomId();
        if (roomId != null) {
            ands.add(cb.equal(from.get(Comment_.room).get(Room_.id), roomId));
        }

        final Integer roomNumber = filter.getRoomNumber();
        if (roomNumber != null) {
            ands.add(cb.equal(from.get(Comment_.room).get(Room_.number), roomNumber));
        }

        final String comment = filter.getComment();
        if (StringUtils.isNotBlank(comment)) {
            ands.add(cb.equal(from.get(Comment_.comment), comment));
        }

        final Integer userAccountId = filter.getUserAccountId();
        if (userAccountId != null) {
            ands.add(cb.equal(from.get(Comment_.userAccount).get(UserAccount_.id), userAccountId));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Comment> from, final String sortColumn) {
        switch (sortColumn) {
        case "room":
            return from.get(Comment_.room).get(Room_.number);
        case "userAccount":
            return from.get(Comment_.userAccount).get(UserAccount_.email);
        case "comment":
            return from.get(Comment_.comment);
        case "created":
            return from.get(Comment_.created);
        case "updated":
            return from.get(Comment_.updated);
        case "id":
            return from.get(Comment_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
