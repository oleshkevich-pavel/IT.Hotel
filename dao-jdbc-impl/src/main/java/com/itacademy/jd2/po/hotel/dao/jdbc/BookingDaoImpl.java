package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IBookingDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Booking;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Room;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.BookingStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class BookingDaoImpl extends AbstractDaoImpl<IBooking, Integer> implements IBookingDao {

    @Override
    public IBooking createEntity() {
        final Booking booking = new Booking();
        booking.setVersion(IBooking.DEFAULT_VERSION);
        return booking;
    }

    @Override
    public void insert(final IBooking entity) {
        executeStatement(new PreparedStatementAction<IBooking>(String.format(
                "insert into %s (room_id, user_account_id, check_in, check_out, room_price, booking_status_id, version, "
                        + "created, updated) values(?,?,?,?,?,?,?,?)",
                getTableName()), true) {
            @Override
            public IBooking doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getRoom().getId());
                pStmt.setInt(2, entity.getUserAccount().getId());
                pStmt.setObject(3, entity.getCheckIn(), Types.TIMESTAMP);
                pStmt.setObject(4, entity.getCheckOut());
                pStmt.setDouble(5, entity.getRoomPrice());
                pStmt.setInt(6, entity.getBookingStatus().getId());
                pStmt.setInt(7, entity.getVersion());
                pStmt.setObject(8, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(9, entity.getUpdated(), Types.TIMESTAMP);

                pStmt.executeUpdate();

                final ResultSet rs = pStmt.getGeneratedKeys();
                rs.next();
                final int id = rs.getInt("id");

                rs.close();

                entity.setId(id);
                return entity;
            }
        });
    }

    @Override
    public void update(final IBooking entity) {
        executeStatement(new PreparedStatementAction<IBooking>(
                String.format("update %s set room_id=?, user_account_id=?, check_in=?, check_out=?, room_price=?,"
                        + "booking_status_id=?, version=?, updated=? where id=?", getTableName()),
                true) {
            @Override
            public IBooking doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getRoom().getId());
                pStmt.setInt(2, entity.getUserAccount().getId());
                pStmt.setObject(3, entity.getCheckIn(), Types.TIMESTAMP);
                pStmt.setObject(4, entity.getCheckOut());
                pStmt.setDouble(5, entity.getRoomPrice());
                pStmt.setInt(6, entity.getBookingStatus().getId());
                pStmt.setInt(7, entity.getVersion());
                pStmt.setObject(8, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(9, entity.getId());

                pStmt.executeUpdate();

                final int i = pStmt.executeUpdate();
                if (i == 1) {
                    return entity;
                }
                throw new RuntimeException("Wrong insert");
            }
        });
    }

    @Override
    protected IBooking parseRow(final ResultSet resultSet) throws SQLException {
        final IBooking entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));

        final IRoom room = new Room();
        room.setId((Integer) resultSet.getObject("room_id"));
        entity.setRoom(room);

        final IUserAccount userAccount = new UserAccount();
        userAccount.setId((Integer) resultSet.getObject("user_account_id"));
        entity.setUserAccount(userAccount);

        entity.setCheckIn(resultSet.getTimestamp("check_in"));
        entity.setCheckOut(resultSet.getTimestamp("check_out"));
        entity.setRoomPrice(resultSet.getDouble("room_price"));

        final IBookingStatus bookingStatus = new BookingStatus();
        bookingStatus.setId((Integer) resultSet.getObject("booking_status_id"));
        entity.setBookingStatus(bookingStatus);

        entity.setVersion((Integer) resultSet.getObject("version"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "booking";
    }

    @Override
    public List<IBooking> find(final BookingFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final BookingFilter filter) {
        return executeCountQuery("");
    }

    @Override
    public Double getMaxPrice() {
        // select max(room_price) from booking
        throw new UnsupportedOperationException();
    }
}
