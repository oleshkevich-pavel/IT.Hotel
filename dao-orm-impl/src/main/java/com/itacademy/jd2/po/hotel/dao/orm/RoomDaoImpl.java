package com.itacademy.jd2.po.hotel.dao.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IRoomDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.dao.orm.model.Booking;
import com.itacademy.jd2.po.hotel.dao.orm.model.Booking_;
import com.itacademy.jd2.po.hotel.dao.orm.model.Room;
import com.itacademy.jd2.po.hotel.dao.orm.model.Room_;

@Repository
public class RoomDaoImpl extends AbstractDaoImpl<IRoom, Integer> implements IRoomDao {

    protected RoomDaoImpl() {
        super(Room.class);
    }

    @Override
    public IRoom createEntity() {
        return new Room();
    }

    @Override
    public List<IRoom> getAllFullInfo() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IRoom> cq = cb.createQuery(IRoom.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(from);

        final TypedQuery<IRoom> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public IRoom getFullInfo(final Integer id) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IRoom> cq = cb.createQuery(IRoom.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(from);
        cq.where(cb.equal(from.get(Room_.id), id));

        final TypedQuery<IRoom> q = em.createQuery(cq);
        List<IRoom> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<IRoom> find(final RoomFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IRoom> cq = cb.createQuery(IRoom.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(from);

        final List<Predicate> ands = applyFilter(filter, cb, cq, from);

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
        applySort(filter, cq, from);

        final TypedQuery<IRoom> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    private void applySort(final RoomFilter filter, final CriteriaQuery<IRoom> cq, final Root<Room> from) {
        final String sortColumn = filter.getSortColumn();
        if (sortColumn != null) {
            final Path<?> expression = getSortPath(from, sortColumn);
            cq.orderBy(new OrderImpl(expression, filter.getSortOrder()));
        }
    }

    private List<Integer> findBookedRoomsByDates(final RoomFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        final Root<Booking> from = cq.from(Booking.class);
        // from.fetch(Booking_.room, JoinType.LEFT);
        cq.select(from.get(Booking_.room).get(Room_.id));
        // cq.select(from.get(Room_.id));
        final Date checkIn = filter.getCheckIn();
        final Date checkOut = filter.getCheckOut();
        if (checkIn != null && checkOut != null) {
            cq.where(cb.and(cb.or(cb.between(from.get(Booking_.checkIn), checkIn, checkOut),
                    cb.between(from.get(Booking_.checkOut), checkIn, checkOut),
                    cb.and(cb.lessThanOrEqualTo(from.get(Booking_.checkIn), checkIn),
                            cb.greaterThanOrEqualTo(from.get(Booking_.checkOut), checkOut)))));
        }
        cq.distinct(true);
        final TypedQuery<Integer> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public List<IRoom> findFreeRooms(final RoomFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<IRoom> cq = cb.createQuery(IRoom.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(from);
        final List<Predicate> ands = applyFilter(filter, cb, cq, from);

        List<Integer> bookedRooms = findBookedRoomsByDates(filter);
        // случай когда все комнаты доступны, падала ошибка "ERROR: syntax error
        // at or near ")""
        if (bookedRooms.size() != 0) {
            ands.add(from.get(Room_.id).in(bookedRooms).not());
        }
        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
        applySort(filter, cq, from);

        final TypedQuery<IRoom> q = em.createQuery(cq);
        setPaging(filter, q);
        return q.getResultList();
    }

    @Override
    public long getCount(final RoomFilter filter) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(cb.count(from));
        final List<Predicate> ands = applyFilter(filter, cb, cq, from);

        if (!ands.isEmpty()) {
            cq.where(cb.and(ands.toArray(new Predicate[0])));
        }
        final TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    private List<Predicate> applyFilter(final RoomFilter filter, final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final Root<Room> from) {
        final List<Predicate> ands = new ArrayList<>();
        final Integer roomNumber = filter.getRoomNumber();

        if (roomNumber != null) {
            ands.add(cb.equal(from.get(Room_.number), roomNumber));
        }

        final Accomodation Accomodation = filter.getAccomodation();
        if (Accomodation != null) {
            ands.add(cb.equal(from.get(Room_.accomodation), Accomodation));
        }

        final ViewType view = filter.getView();
        if (view != null) {
            ands.add(cb.equal(from.get(Room_.view), view));
        }
        
        final Boolean dirty = filter.getDirty();
        if (dirty != null) {
            ands.add(cb.equal(from.get(Room_.dirty), dirty));
        }
        
        final Boolean broken = filter.getBroken();
        if (broken != null) {
            ands.add(cb.equal(from.get(Room_.broken), broken));
        }
        
        final Double priceMin = filter.getPriceMin();
        final Double priceMax = filter.getPriceMax();
        if (priceMin != null) {
            if (priceMax != null) {
                ands.add(cb.between(from.get(Room_.actualPrice), priceMin, priceMax));
            } else {
                ands.add(cb.greaterThanOrEqualTo(from.get(Room_.actualPrice), priceMin));
            }
        } else if (priceMax != null) {
            ands.add(cb.lessThanOrEqualTo(from.get(Room_.actualPrice), priceMax));
        }

        return ands;
    }

    @Override
    public Double getMaxPrice() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        final Root<Room> from = cq.from(Room.class);
        cq.select(cb.max(from.get(Room_.actualPrice)));

        final TypedQuery<Double> q = em.createQuery(cq);
        List<Double> resultList = q.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    private Path<?> getSortPath(final Root<Room> from, final String sortColumn) {
        switch (sortColumn) {
        case "number":
            return from.get(Room_.number);
        case "floor":
            return from.get(Room_.floor);
        case "type":
            return from.get(Room_.type);
        case "accomodation":
            return from.get(Room_.accomodation);
        case "view":
            return from.get(Room_.view);
        case "actualPrice":
            return from.get(Room_.actualPrice);
        case "description":
            return from.get(Room_.description);
        case "dirty":
            return from.get(Room_.dirty);
        case "broken":
            return from.get(Room_.broken);
        case "created":
            return from.get(Room_.created);
        case "updated":
            return from.get(Room_.updated);
        case "id":
            return from.get(Room_.id);
        default:
            throw new UnsupportedOperationException("sorting is not supported by column:" + sortColumn);
        }
    }
}
