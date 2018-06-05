package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IEmployeeDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.orm.model.Employee;
import com.itacademy.jd2.po.hotel.dao.orm.model.Employee_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Post_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class EmployeeDaoImpl extends AbstractDaoImpl<IEmployee, Integer> implements IEmployeeDao {

    protected EmployeeDaoImpl() {
        super(Employee.class);
    }

    @Override
    public IEmployee createEntity() {
        return new Employee();
    }
    
    @Override
    public List<IEmployee> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IEmployee> cq = cb.createQuery(IEmployee.class);
        final Root<Employee> from = cq.from(Employee.class);
        cq.select(from);

        from.fetch(Employee_.userAccount, JoinType.LEFT);
        from.fetch(Employee_.post, JoinType.LEFT);

        final TypedQuery<IEmployee> q = em.createQuery(cq);
        return q.getResultList();
    }
    
    @Override
    public IEmployee getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IEmployee> cq = cb.createQuery(IEmployee.class);
        final Root<Employee> from = cq.from(Employee.class);
        cq.select(from);

        from.fetch(Employee_.userAccount, JoinType.LEFT);
        from.fetch(Employee_.post, JoinType.LEFT);

        cq.where(cb.equal(from.get(Employee_.id), id));

        final TypedQuery<IEmployee> q = em.createQuery(cq);
        List<IEmployee> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IEmployee> find(final EmployeeFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IEmployee> cq = cb.createQuery(IEmployee.class);
        final Root<Employee> from = cq.from(Employee.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(Employee_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchPost()) {
            from.fetch(Employee_.post, JoinType.LEFT);
        }

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IEmployee> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final EmployeeFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Employee> from = cq.from(Employee.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<Employee> from, final String sortColumn) {
        switch (sortColumn) {
        case "email":
            return from.get(Employee_.userAccount).get(UserAccount_.email);
        case "password":
            return from.get(Employee_.userAccount).get(UserAccount_.password);
        case "role":
            return from.get(Employee_.userAccount).get(UserAccount_.role);
        case "firstName":
            return from.get(Employee_.userAccount).get(UserAccount_.firstName);
        case "lastName":
            return from.get(Employee_.userAccount).get(UserAccount_.lastName);
        case "birthday":
            return from.get(Employee_.userAccount).get(UserAccount_.birthday);
        case "address":
            return from.get(Employee_.userAccount).get(UserAccount_.address);
        case "phone":
            return from.get(Employee_.userAccount).get(UserAccount_.phone);
        case "hiring":
            return from.get(Employee_.hiring);
        case "layoff":
            return from.get(Employee_.layoff);
        case "post":
            return from.get(Employee_.post).get(Post_.name);
        case "created":
            return from.get(Employee_.created);
        case "updated":
            return from.get(Employee_.updated);
        case "id":
            return from.get(Employee_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
