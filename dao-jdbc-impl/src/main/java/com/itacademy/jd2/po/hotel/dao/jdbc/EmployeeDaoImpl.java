package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IEmployeeDao;
import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Employee;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Post;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class EmployeeDaoImpl extends AbstractDaoImpl<IEmployee, Integer> implements IEmployeeDao {

    @Autowired
    private IUserAccountDao userAccountDao;

    @Override
    public IEmployee createEntity() {
        return new Employee();
    }

    @Override
    public void insert(final IEmployee entity) {
        executeStatement(new PreparedStatementAction<IEmployee>(
                String.format("insert into %s (id, hiring, layoff, post_id, created, updated) values(?,?,?,?,?,?)",
                        getTableName()),
                true) {
            @Override
            public IEmployee doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getId());
                pStmt.setObject(2, entity.getHiring(), Types.TIMESTAMP);
                pStmt.setObject(3, entity.getLayoff(), Types.TIMESTAMP);
                pStmt.setInt(4, entity.getPost().getId());
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
    public void update(final IEmployee entity) {
        executeStatement(new PreparedStatementAction<IEmployee>(
                String.format("update %s set hiring=?, layoff=?, post_id=?, updated=? where id=?", getTableName()),
                true) {
            @Override
            public IEmployee doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setObject(1, entity.getHiring(), Types.TIMESTAMP);
                pStmt.setObject(2, entity.getLayoff(), Types.TIMESTAMP);
                pStmt.setInt(3, entity.getPost().getId());
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
    protected IEmployee parseRow(final ResultSet resultSet, final Set<String> columns) throws SQLException {
        final IEmployee entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setHiring(resultSet.getTimestamp("hiring"));
        entity.setLayoff(resultSet.getTimestamp("layoff"));

        final Integer postId = (Integer) resultSet.getObject("post_id");
        if (postId != null) {
            final IPost post = new Post();
            post.setId(postId);
            if (columns.contains("post_name")) {
                post.setName(resultSet.getString("post_name"));
            }
            entity.setPost(post);
        }

        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    public IEmployee getFullInfo(final Integer id) {
        final IEmployee employee = get(id);

        employee.setUserAccount(userAccountDao.get(id));
        return employee;
    }

    @Override
    protected String getTableName() {
        return "employee";
    }

    @Override
    public List<IEmployee> find(final EmployeeFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendJOINs(sql, filter);
        appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final EmployeeFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendJOINs(sql, filter);
        appendWHEREs(sql, filter);
        return executeCountQuery(sql.toString());
    }

    private void appendJOINs(final StringBuilder sb, final EmployeeFilter filter) {
    }

    private void appendWHEREs(final StringBuilder sb, final EmployeeFilter filter) {
        // nothing yet
    }

}
