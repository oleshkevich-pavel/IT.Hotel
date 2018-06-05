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

import com.itacademy.jd2.po.hotel.dao.api.IMessageDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.dao.orm.model.Message;
import com.itacademy.jd2.po.hotel.dao.orm.model.Message_;

@Repository
public class MessageDaoImpl extends AbstractDaoImpl<IMessage, Integer> implements IMessageDao {

    protected MessageDaoImpl() {
        super(Message.class);
    }

    @Override
    public IMessage createEntity() {
        return new Message();
    }

    @Override
    public List<IMessage> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMessage> cq = cb.createQuery(IMessage.class);
        final Root<Message> from = cq.from(Message.class);
        cq.select(from);

        final TypedQuery<IMessage> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IMessage getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMessage> cq = cb.createQuery(IMessage.class);
        final Root<Message> from = cq.from(Message.class);
        cq.select(from);

        cq.where(cb.equal(from.get(Message_.id), id));

        final TypedQuery<IMessage> q = em.createQuery(cq);
        List<IMessage> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IMessage> find(final MessageFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IMessage> cq = cb.createQuery(IMessage.class);
        final Root<Message> from = cq.from(Message.class);
        cq.select(from);

        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IMessage> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final MessageFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Message> from = cq.from(Message.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final MessageFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Message> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String name = filter.getName();
        if (StringUtils.isNotBlank(name)) {
            ands.add(cb.equal(from.get(Message_.name), name));
        }

        final String phone = filter.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            ands.add(cb.equal(from.get(Message_.phone), phone));
        }

        final String email = filter.getEmail();
        if (StringUtils.isNotBlank(email)) {
            ands.add(cb.equal(from.get(Message_.email), email));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Message> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(Message_.name);
        case "phone":
            return from.get(Message_.phone);
        case "email":
            return from.get(Message_.email);
        case "message":
            return from.get(Message_.message);
        case "created":
            return from.get(Message_.created);
        case "updated":
            return from.get(Message_.updated);
        case "id":
            return from.get(Message_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
