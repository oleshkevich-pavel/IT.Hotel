package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IGuestStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.GuestStatus;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class GuestStatusDaoImpl extends AbstractDaoImpl<IGuestStatus, Integer> implements IGuestStatusDao {

    @Override
    public IGuestStatus createEntity() {
        return new GuestStatus();
    }

    @Override
    public void insert(final IGuestStatus entity) {
        executeStatement(new PreparedStatementAction<IGuestStatus>(
                String.format("insert into %s (name, discount, created, updated) values(?,?,?,?)", getTableName()),
                true) {
            @Override
            public IGuestStatus doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setInt(2, entity.getDiscount());
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
    public void update(final IGuestStatus entity) {
        executeStatement(new PreparedStatementAction<IGuestStatus>(
                String.format("update %s set name=?, discount=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IGuestStatus doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setInt(2, entity.getDiscount());
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
        return "guest_status";
    }

    @Override
    protected IGuestStatus parseRow(final ResultSet resultSet) throws SQLException {
        final IGuestStatus entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDiscount((Integer) resultSet.getObject("discount"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));
        return entity;
    }

    @Override
    public List<IGuestStatus> find(final GuestStatusFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final GuestStatusFilter filter) {
        return executeCountQuery("");
    }

    @Override
    public Integer getMinDiscount() {
        throw new UnsupportedOperationException();
    }
}
