package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IBookingStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.BookingStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class BookingStatusDaoImpl extends AbstractDaoImpl<IBookingStatus, Integer> implements IBookingStatusDao {

    @Override
    public IBookingStatus createEntity() {
        return new BookingStatus();
    }

    @Override
    public void insert(final IBookingStatus entity) {
        executeStatement(new PreparedStatementAction<IBookingStatus>(
                String.format("insert into %s (name, color, created, updated) values(?,?,?,?)", getTableName()), true) {
            @Override
            public IBookingStatus doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getColor());
                pStmt.setObject(3, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IBookingStatus entity) {
        executeStatement(new PreparedStatementAction<IBookingStatus>(
                String.format("update %s set name=?, color=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IBookingStatus doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getColor());
                pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(4, entity.getId());

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
    protected String getTableName() {
        return "booking_status";
    }

    @Override
    protected IBookingStatus parseRow(final ResultSet resultSet) throws SQLException {
        final IBookingStatus entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setColor(resultSet.getString("color"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));
        return entity;
    }

    @Override
    public List<IBookingStatus> find(final BookingStatusFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final BookingStatusFilter filter) {
        return executeCountQuery("");
    }
}
