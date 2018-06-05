package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IBookedMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.BookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Maintenance;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Room;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class BookedMaintenanceDaoImpl extends AbstractDaoImpl<IBookedMaintenance, Integer>
        implements IBookedMaintenanceDao {

    @Override
    public IBookedMaintenance createEntity() {
        return new BookedMaintenance();
    }

    @Override
    public void insert(final IBookedMaintenance entity) {
        executeStatement(new PreparedStatementAction<IBookedMaintenance>(
                String.format("insert into %s (user_account_id, room_id, maintenance_id, "
                        + "time, price, created, updated) values(?,?,?,?,?,?,?)", getTableName()),
                true) {
            @Override
            public IBookedMaintenance doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getUserAccount().getId());
                pStmt.setInt(2, entity.getRoom().getId());
                pStmt.setInt(3, entity.getMaintenance().getId());
                pStmt.setObject(4, entity.getTime(), Types.TIMESTAMP);
                pStmt.setDouble(5, entity.getPrice());
                pStmt.setObject(6, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(7, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IBookedMaintenance entity) {
        executeStatement(new PreparedStatementAction<IBookedMaintenance>(
                String.format("update %s set user_account_id=?, room_id=?, maintenance_id=?, "
                        + "time=?, price=?, updated=? where id=?", getTableName()),
                true) {
            @Override
            public IBookedMaintenance doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getUserAccount().getId());
                pStmt.setInt(2, entity.getRoom().getId());
                pStmt.setInt(3, entity.getMaintenance().getId());
                pStmt.setObject(4, entity.getTime(), Types.TIMESTAMP);
                pStmt.setDouble(5, entity.getPrice());
                pStmt.setObject(6, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setInt(7, entity.getId());

                final int i = pStmt.executeUpdate();
                if (i == 1) {
                    return entity;
                }
                throw new RuntimeException("Wrong insert");
            }
        });
    }

    @Override
    protected IBookedMaintenance parseRow(final ResultSet resultSet, final Set<String> columns) throws SQLException {
        final IBookedMaintenance entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));

        final Integer userAccountId = (Integer) resultSet.getObject("user_account_id");
        if (userAccountId != null) {
            final IUserAccount userAccount = new UserAccount();
            userAccount.setId(userAccountId);
            if (columns.contains("user_account_last_name")) {
                userAccount.setLastName(resultSet.getString("user_account_last_name"));
            }
            entity.setUserAccount(userAccount);
        }

        final Integer roomId = (Integer) resultSet.getObject("room_id");
        if (roomId != null) {
            final IRoom room = new Room();
            room.setId(roomId);
            if (columns.contains("room_number")) {
                room.setNumber((Integer) resultSet.getObject("room_number"));
            }
            entity.setRoom(room);
        }

        final Integer maintenanceId = (Integer) resultSet.getObject("maintenance_id");
        if (maintenanceId != null) {
            final IMaintenance maintenance = new Maintenance();
            maintenance.setId(maintenanceId);
            if (columns.contains("maintenance_name")) {
                maintenance.setName(resultSet.getString("maintenance_name"));
            }
            entity.setMaintenance(maintenance);
        }

        entity.setTime(resultSet.getTimestamp("time"));
        entity.setPrice(resultSet.getDouble("price"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "booked_maintenance";
    }

    @Override
    public List<IBookedMaintenance> find(final BookedMaintenanceFilter filter) {
        final StringBuilder sql = new StringBuilder("select booked_maintenance.*");
        if (filter.getFetchRoom()) {
            sql.append(String.format(", room.number as room_number"));
        }
        if (filter.getFetchUserAccount()) {
            sql.append(String.format(", user_account.last_name as user_account_last_name"));
        }
        if (filter.getFetchMaintenance()) {
            sql.append(String.format(", maintenance.name as maintenance_name"));
        }
        sql.append(String.format(" from %s", getTableName()));

        appendJOINs(sql, filter);
        // appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQueryWithCustomSelect(sql.toString());
    }

    @Override
    public long getCount(final BookedMaintenanceFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendJOINs(sql, filter);
        return executeCountQuery(sql.toString());
    }

    private void appendJOINs(final StringBuilder sb, final BookedMaintenanceFilter filter) {
        if (filter.getFetchRoom()) {
            sb.append(" join room on (room.id=booked_maintenance.room_id) ");
        }
        if (filter.getFetchUserAccount()) {
            sb.append(" join user_account on (user_account.id=booked_maintenance.user_account_id) ");
        }
        if (filter.getFetchMaintenance()) {
            sb.append(" join maintenance on (maintenance.id=booked_maintenance.maintenance_id) ");
        }
    }

    @Override
    public Double getMaxPrice() {
        throw new UnsupportedOperationException();
    }
}
