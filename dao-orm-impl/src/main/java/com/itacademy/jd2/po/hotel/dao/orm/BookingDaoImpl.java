package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IBookingDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.dao.orm.model.Booking;
import com.itacademy.jd2.po.hotel.dao.orm.model.Booking_;
import com.itacademy.jd2.po.hotel.dao.orm.model.BookingStatus_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Room_;
import com.itacademy.jd2.po.hotel.dao.orm.model.UserAccount_;

@Repository
public class BookingDaoImpl extends AbstractDaoImpl<IBooking, Integer> implements IBookingDao {

    @PersistenceContext
    private EntityManager entityManager;

    protected BookingDaoImpl() {
        super(Booking.class);
    }

    @Override
    public IBooking createEntity() {
        final Booking booking = new Booking();
        booking.setVersion(IBooking.DEFAULT_VERSION);
        return booking;
    }

    @Override
    public List<IBooking> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBooking> cq = cb.createQuery(IBooking.class);
        final Root<Booking> from = cq.from(Booking.class);
        cq.select(from);

        from.fetch(Booking_.userAccount, JoinType.LEFT);
        from.fetch(Booking_.room, JoinType.LEFT);
        from.fetch(Booking_.bookingStatus, JoinType.LEFT);

        final TypedQuery<IBooking> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IBooking getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBooking> cq = cb.createQuery(IBooking.class);
        final Root<Booking> from = cq.from(Booking.class);
        cq.select(from);

        from.fetch(Booking_.userAccount, JoinType.LEFT);
        from.fetch(Booking_.room, JoinType.LEFT);
        from.fetch(Booking_.bookingStatus, JoinType.LEFT);

        cq.where(cb.equal(from.get(Booking_.id), id));

        final TypedQuery<IBooking> q = em.createQuery(cq);
        List<IBooking> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IBooking> find(final BookingFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IBooking> cq = cb.createQuery(IBooking.class);
        final Root<Booking> from = cq.from(Booking.class);
        cq.select(from);

        if (filter.getFetchUserAccount()) {
            from.fetch(Booking_.userAccount, JoinType.LEFT);
        }

        if (filter.getFetchRoom()) {
            from.fetch(Booking_.room, JoinType.LEFT);
        }

        if (filter.getFetchBookingStatus()) {
            from.fetch(Booking_.bookingStatus, JoinType.LEFT);
        }
        applyFilter(filter, cb, cq, from);

        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }

        final TypedQuery<IBooking> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final BookingFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Booking> from = cq.from(Booking.class);
        cq.select(cb.count(from));
        applyFilter(filter, cb, cq, from);
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private void applyFilter(final BookingFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Booking> from) {
        final List<Predicate> ands = new ArrayList<>();

        final Integer roomNumberId = filter.getRoomNumberId();
        final Integer bookingId = filter.getId();
        if (filter.isUpdate()) {
            // не должно выдавать, что нельзя апдейтнуть, если даты подходящие
            // из-за того, что при проверке сам этот номер попал в результат.
            ands.add(cb.equal(from.get(Booking_.id), bookingId).not());
        }
        if (roomNumberId != null) {
            ands.add(cb.equal(from.get(Booking_.room).get(Room_.id), roomNumberId));
        }

        final Date checkIn = filter.getCheckIn();
        final Date checkOut = filter.getCheckOut();
        if (checkIn != null && checkOut != null) {
            ands.add(cb.or(cb.between(from.get(Booking_.checkIn), checkIn, checkOut),
                    cb.between(from.get(Booking_.checkOut), checkIn, checkOut),
                    cb.and(cb.lessThanOrEqualTo(from.get(Booking_.checkIn), checkIn),
                            cb.greaterThanOrEqualTo(from.get(Booking_.checkOut), checkOut))));
        }

        final Integer userAccountId = filter.getUserAccountId();
        if (userAccountId != null) {
            ands.add(cb.equal(from.get(Booking_.userAccount).get(UserAccount_.id), userAccountId));
        }

        final Integer bookingStatusId = filter.getBookingStatusId();
        if (bookingStatusId != null) {
            ands.add(cb.equal(from.get(Booking_.bookingStatus).get(BookingStatus_.id), bookingStatusId));
        }

        final Accomodation accomodation = filter.getAccomodation();
        if (accomodation != null) {
            ands.add(cb.equal(from.get(Booking_.room).get(Room_.accomodation), accomodation));
        }

        final ViewType view = filter.getView();
        if (view != null) {
            ands.add(cb.equal(from.get(Booking_.room).get(Room_.view), view));
        }

        final Double priceMin = filter.getPriceMin();
        final Double priceMax = filter.getPriceMax();
        if (priceMin != null) {
            if (priceMax != null) {
                ands.add(cb.between(from.get(Booking_.roomPrice), priceMin, priceMax));
            } else {
                ands.add(cb.greaterThanOrEqualTo(from.get(Booking_.roomPrice), priceMin));
            }
        } else if (priceMax != null) {
            ands.add(cb.lessThanOrEqualTo(from.get(Booking_.roomPrice), priceMax));
        }

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
    }

    private Path<?> getSortPath(final Root<Booking> from, final String sortColumn) {
        switch (sortColumn) {
        case "room":
            return from.get(Booking_.room).get(Room_.number);
        case "userAccount":
            return from.get(Booking_.userAccount).get(UserAccount_.email);
        case "checkIn":
            return from.get(Booking_.checkIn);
        case "checkOut":
            return from.get(Booking_.checkOut);
        case "roomPrice":
            return from.get(Booking_.roomPrice);
        case "bookingStatus":
            return from.get(Booking_.bookingStatus).get(BookingStatus_.name);
        case "created":
            return from.get(Booking_.created);
        case "updated":
            return from.get(Booking_.updated);
        case "id":
            return from.get(Booking_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }

    @Override
    public Double getMaxPrice() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        final Root<Booking> from = cq.from(Booking.class);
        cq.select(cb.max(from.get(Booking_.roomPrice)));

        final TypedQuery<Double> q = em.createQuery(cq);
        List<Double> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public void insert(final IBooking entity) throws PersistenceException {
        if (validPeriod(entity)) {
            try {
                entityManager.persist(entity);
            } catch (PersistenceException e) {
                throw new PersistenceException();
            }
        } else {
            throw new PersistenceException();
        }
    }

    @Override
    public void update(IBooking entity) throws PersistenceException {
        if (validPeriod(entity)) {
            try {
                entity = entityManager.merge(entity);
                entityManager.flush();
            } catch (PersistenceException e) {
                throw new PersistenceException();
            }
        } else {
            throw new PersistenceException();
        }
    }

    private boolean validPeriod(IBooking entity) {
        final BookingFilter filter = new BookingFilter();
        if (entity.getId() != null) {
            filter.setUpdate(true);
            filter.setId(entity.getId());
        }
        filter.setFetchRoom(true);
        filter.setRoomNumberId(entity.getRoom().getId());
        filter.setCheckIn(entity.getCheckIn());
        filter.setCheckOut(entity.getCheckOut());

        final List<IBooking> entities = find(filter);
        return entities.isEmpty();
    }
}
