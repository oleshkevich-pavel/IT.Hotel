package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IMessageDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Message;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class MessageDaoImpl extends AbstractDaoImpl<IMessage, Integer> implements IMessageDao {

    @Override
    public IMessage createEntity() {
        return new Message();
    }

    @Override
    public void insert(final IMessage entity) {
        executeStatement(new PreparedStatementAction<IMessage>(
                String.format("insert into %s (name, phone, email, message, created, updated) values(?,?,?,?,?,?)",
                        getTableName()),
                true) {
            @Override
            public IMessage doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getPhone());
                pStmt.setString(3, entity.getEmail());
                pStmt.setString(4, entity.getMessage());
                pStmt.setObject(5, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(6, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IMessage entity) {
        executeStatement(new PreparedStatementAction<IMessage>(String.format(
                "update %s set name=?, phone=?, email=?, message=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IMessage doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getPhone());
                pStmt.setString(3, entity.getEmail());
                pStmt.setString(4, entity.getMessage());
                pStmt.setObject(5, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(6, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(7, entity.getId());

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
    protected IMessage parseRow(final ResultSet resultSet) throws SQLException {
        final IMessage entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setPhone(resultSet.getString("phone"));
        entity.setEmail(resultSet.getString("email"));
        entity.setMessage(resultSet.getString("message"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "message";
    }

    @Override
    public List<IMessage> find(final MessageFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final MessageFilter filter) {
        return executeCountQuery("");
    }
}
