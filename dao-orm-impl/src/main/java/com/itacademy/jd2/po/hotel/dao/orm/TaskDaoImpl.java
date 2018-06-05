package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.itacademy.jd2.po.hotel.dao.api.ITaskDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.dao.orm.model.Task;
import com.itacademy.jd2.po.hotel.dao.orm.model.Task_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class TaskDaoImpl extends AbstractDaoImpl<ITask, Integer> implements ITaskDao {

    protected TaskDaoImpl() {
        super(Task.class);
    }

    @Override
    public ITask createEntity() {
        final Task task = new Task();
        task.setVersion(ITask.DEFAULT_VERSION);
        return task;
    }

    @Override
    public List<ITask> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ITask> cq = cb.createQuery(ITask.class);
        final Root<Task> from = cq.from(Task.class);
        cq.select(from);

        from.fetch(Task_.answerable, JoinType.LEFT);
        from.fetch(Task_.creator, JoinType.LEFT);

        final TypedQuery<ITask> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public ITask getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ITask> cq = cb.createQuery(ITask.class);
        final Root<Task> from = cq.from(Task.class);
        cq.select(from);

        from.fetch(Task_.answerable, JoinType.LEFT);
        from.fetch(Task_.creator, JoinType.LEFT);

        cq.where(cb.equal(from.get(Task_.id), id));

        final TypedQuery<ITask> q = em.createQuery(cq);
        List<ITask> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<ITask> find(final TaskFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ITask> cq = cb.createQuery(ITask.class);
        final Root<Task> from = cq.from(Task.class);
        cq.select(from);

        if (filter.getFetchAnswerable()) {
            from.fetch(Task_.answerable, JoinType.LEFT);
        }

        if (filter.getFetchCreator()) {
            from.fetch(Task_.creator, JoinType.LEFT);
        }
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<ITask> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final TaskFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Task> from = cq.from(Task.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final TaskFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Task> from) {
        final List<Predicate> ands = new ArrayList<>();

        final String toDo = filter.getToDo();
        if (StringUtils.isNotBlank(toDo)) {
            ands.add(cb.equal(from.get(Task_.toDo), toDo));
        }

        final String description = filter.getDescription();
        if (StringUtils.isNotBlank(description)) {
            ands.add(cb.equal(from.get(Task_.description), description));
        }

        final TaskPriority priority = filter.getPriority();
        if (priority != null) {
            ands.add(cb.equal(from.get(Task_.priority), priority));
        }

        final Date executionDate = filter.getExecutionDate();
        if (executionDate != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(executionDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date endDay = calendar.getTime();
            ands.add(cb.between(from.get(Task_.executionTime), executionDate, endDay));
        }

        final Integer answerableId = filter.getAnswerableId();
        if (answerableId != null) {
            ands.add(cb.equal(from.get(Task_.answerable).get(UserAccount_.id), answerableId));
        }

        final Boolean executed = filter.getExecuted();
        if (Boolean.FALSE.equals(executed)) {
            ands.add(cb.equal(from.get(Task_.executed), executed));
        }

        final Integer creatorId = filter.getCreatorId();
        if (creatorId != null) {
            ands.add(cb.equal(from.get(Task_.creator).get(UserAccount_.id), creatorId));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Task> from, final String sortColumn) {
        switch (sortColumn) {
        case "toDo":
            return from.get(Task_.toDo);
        case "description":
            return from.get(Task_.description);
        case "priority":
            return from.get(Task_.priority);
        case "executionTime":
            return from.get(Task_.executionTime);
        case "answerable":
            return from.get(Task_.answerable).get(UserAccount_.email);
        case "executed":
            return from.get(Task_.executed);
        case "creator":
            return from.get(Task_.creator).get(UserAccount_.email);
        case "created":
            return from.get(Task_.created);
        case "updated":
            return from.get(Task_.updated);
        case "id":
            return from.get(Task_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
