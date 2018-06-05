package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IPostDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Post;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class PostDaoImpl extends AbstractDaoImpl<IPost, Integer> implements IPostDao {

    @Override
    public IPost createEntity() {
        return new Post();
    }

    @Override
    public void insert(final IPost entity) {
        executeStatement(new PreparedStatementAction<IPost>(
                String.format("insert into %s (name, description, supervisor_id, created, updated) values(?,?,?,?,?)",
                        getTableName()),
                true) {
            @Override
            public IPost doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                if (entity.getSupervisor() == null) {
                    pStmt.setNull(3, Types.INTEGER);
                } else {
                    pStmt.setInt(3, entity.getSupervisor().getId());
                }
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
    public void update(final IPost entity) {
        executeStatement(new PreparedStatementAction<IPost>(String.format(
                "update %s set name=?, description=?, supervisor_id=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IPost doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setString(2, entity.getDescription());
                if (entity.getSupervisor() == null) {
                    pStmt.setNull(3, Types.INTEGER);
                } else {
                    pStmt.setInt(3, entity.getSupervisor().getId());
                }
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
    protected IPost parseRow(final ResultSet resultSet) throws SQLException {
        final IPost entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));

        final IPost supervisor = createEntity();
        supervisor.setId((Integer) resultSet.getObject("supervisor_id"));
        if (supervisor.getId() == null) {
            entity.setSupervisor(null);
        } else {
            entity.setSupervisor(supervisor);
        }

        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "post";
    }

    @Override
    public List<IPost> find(final PostFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final PostFilter filter) {
        return executeCountQuery("");
    }
}
