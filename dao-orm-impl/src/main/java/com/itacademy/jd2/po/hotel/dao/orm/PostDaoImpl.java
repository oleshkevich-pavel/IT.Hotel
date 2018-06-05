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

import com.itacademy.jd2.po.hotel.dao.api.IPostDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.orm.model.Post;
import com.itacademy.jd2.po.hotel.dao.orm.model.Post_;

@Repository
public class PostDaoImpl extends AbstractDaoImpl<IPost, Integer> implements IPostDao {

    protected PostDaoImpl() {
        super(Post.class);
    }

    @Override
    public IPost createEntity() {
        return new Post();
    }

    @Override
    public List<IPost> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IPost> cq = cb.createQuery(IPost.class);
        final Root<Post> from = cq.from(Post.class);
        cq.select(from);

        from.fetch(Post_.supervisor, JoinType.LEFT);

        final TypedQuery<IPost> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IPost getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IPost> cq = cb.createQuery(IPost.class);
        final Root<Post> from = cq.from(Post.class);
        cq.select(from);

        from.fetch(Post_.supervisor, JoinType.LEFT);
        // cq.distinct(true); для избежания дублирования столбцов в result, если
        // здесь у нас 2many
        // при этом возникает косяк с пагинацией, поэтому в этом случае не
        // делаем пагинацию вообще
        // здесь делаем его ,поскольку singleresult

        // ... where id=...
        cq.where(cb.equal(from.get(Post_.id), id));

        final TypedQuery<IPost> q = em.createQuery(cq);
        List<IPost> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IPost> find(final PostFilter filter) {
        final EntityManager em = getEntityManager();// получить доступ к бд
        final CriteriaBuilder cb = em.getCriteriaBuilder();// получаем служебный
                                                           // объект
                                                           // CriteriaBuilder
        final CriteriaQuery<IPost> cq = cb.createQuery(IPost.class);
        // создаем пустой запрос и определяем какой тип будем брать из базы
        final Root<Post> from = cq.from(Post.class);// "генерит select from
                                                    // post"
        cq.select(from);// выбираем что будет добавлено в ResultSet, т.е. что
                        // вместо *, т.е. select * from post

        if (filter.getFetchPost()) {
            // select p,s from post left join supervisor ...
            from.fetch(Post_.supervisor, JoinType.LEFT);
        }

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IPost> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();// execute запроса
    }

    @Override
    public long getCount(final PostFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Post> from = cq.from(Post.class);
        cq.select(cb.count(from));
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private Path<?> getSortPath(final Root<Post> from, final String sortColumn) {
        switch (sortColumn) {
        case "name":
            return from.get(Post_.name);
        case "description":
            return from.get(Post_.description);
        case "supervisor":
            return from.get(Post_.supervisor).get(Post_.name);
        case "created":
            return from.get(Post_.created);
        case "updated":
            return from.get(Post_.updated);
        case "id":
            return from.get(Post_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
