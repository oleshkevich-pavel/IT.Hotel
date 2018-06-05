package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IUnstructuredObjectDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class UnstructuredObjectDaoImpl extends AbstractDaoImpl<IUnstructuredObject, Integer>
        implements IUnstructuredObjectDao {

    @Override
    public IUnstructuredObject createEntity() {
        return new UnstructuredObject();
    }

    @Override
    public void insert(final IUnstructuredObject entity) {
        executeStatement(new PreparedStatementAction<IUnstructuredObject>(
                String.format("insert into %s (name, content, user_account_id, created, updated) values(?,?,?,?,?)",
                        getTableName()),
                true) {
            @Override
            public IUnstructuredObject doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setBytes(2, entity.getContent());
                pStmt.setInt(3, entity.getUserAccount().getId());
                pStmt.setObject(4, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IUnstructuredObject entity) {
        executeStatement(new PreparedStatementAction<IUnstructuredObject>(String.format(
                "update %s set name=?, content=?, user_account_id=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IUnstructuredObject doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setBytes(2, entity.getContent());
                pStmt.setInt(3, entity.getUserAccount().getId());
                pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(5, entity.getId());

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
    protected IUnstructuredObject parseRow(final ResultSet resultSet) throws SQLException {
        final IUnstructuredObject entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setContent(resultSet.getBytes("content"));

        final IUserAccount userAccount = new UserAccount();
        userAccount.setId((Integer) resultSet.getObject("user_account_id"));
        entity.setUserAccount(userAccount);

        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "unstructured_object";
    }

    @Override
    public List<IUnstructuredObject> find(final UnstructuredObjectFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final UnstructuredObjectFilter filter) {
        return executeCountQuery("");
    }
}
