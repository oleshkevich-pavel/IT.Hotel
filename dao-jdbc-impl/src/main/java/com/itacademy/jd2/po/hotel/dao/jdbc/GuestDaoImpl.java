package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IGuestDao;
import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Guest;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.GuestStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class GuestDaoImpl extends AbstractDaoImpl<IGuest, Integer> implements IGuestDao {

    @Autowired
    private IUserAccountDao userAccountDao;

    @Override
    public IGuest createEntity() {
        return new Guest();
    }

    @Override
    public void insert(final IGuest entity) {
        executeStatement(new PreparedStatementAction<IGuest>(String.format(
                "insert into %s (id, verify_key, verified, guest_status_id, credit, created, updated) values(?,?,?,?,?)",
                getTableName()), true) {
            @Override
            public IGuest doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getId());
                pStmt.setString(2, entity.getVerifyKey());
                pStmt.setBoolean(3, entity.getVerified());
                pStmt.setInt(4, entity.getGuestStatus().getId());
                pStmt.setDouble(5, entity.getCredit());
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
    public void update(final IGuest entity) {
        executeStatement(new PreparedStatementAction<IGuest>(String.format(
                "update %s set verify_key=?, verified=?, guest_status_id=?, credit=?, updated=? where id=?",
                getTableName()), true) {
            @Override
            public IGuest doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getVerifyKey());
                pStmt.setBoolean(2, entity.getVerified());
                pStmt.setInt(3, entity.getGuestStatus().getId());
                pStmt.setDouble(4, entity.getCredit());
                pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(6, entity.getId());

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
    protected IGuest parseRow(final ResultSet resultSet, final Set<String> columns) throws SQLException {
        final IGuest entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));

        entity.setVerifyKey(resultSet.getString("verify_key"));
        entity.setVerified(resultSet.getBoolean("verified"));
        entity.setCredit(resultSet.getDouble("credit"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        final Integer guestStatusId = (Integer) resultSet.getObject("guest_status_id");
        if (guestStatusId != null) {
            final IGuestStatus guestStatus = new GuestStatus();
            guestStatus.setId(guestStatusId);
            if (columns.contains("guest_status_name")) {
                guestStatus.setName(resultSet.getString("guest_status_name"));
            }
            entity.setGuestStatus(guestStatus);
        }
        return entity;
    }

    @Override
    public IGuest getFullInfo(final Integer id) {
        final IGuest guest = get(id);

        guest.setUserAccount(userAccountDao.get(id));
        return guest;
    }

    @Override
    protected String getTableName() {
        return "guest";
    }

    @Override
    public List<IGuest> find(final GuestFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendJOINs(sql, filter);
        appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final GuestFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendJOINs(sql, filter);
        appendWHEREs(sql, filter);
        return executeCountQuery(sql.toString());
    }

    private void appendJOINs(final StringBuilder sb, final GuestFilter filter) {
    }

    private void appendWHEREs(final StringBuilder sb, final GuestFilter filter) {
        // nothing yet
    }
}
