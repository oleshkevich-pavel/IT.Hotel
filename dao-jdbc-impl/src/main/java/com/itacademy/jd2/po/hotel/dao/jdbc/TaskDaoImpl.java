package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.ITaskDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.TaskPriority;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Task;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class TaskDaoImpl extends AbstractDaoImpl<ITask, Integer> implements ITaskDao {

    @Override
    public ITask createEntity() {
        return new Task();
    }

    @Override
    public void insert(final ITask entity) {
        executeStatement(new PreparedStatementAction<ITask>(String.format(
                "insert into %s (to_do, description, priority, execution_time, answerable_id, executed, creator_id,"
                + " version, created, updated) values(?,?,?,?,?,?,?,?,?)",
                getTableName()), true) {
            @Override
            public ITask doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getToDo());
                pStmt.setString(2, entity.getDescription());
                pStmt.setString(3, entity.getPriority().name());
                pStmt.setObject(4, entity.getExecutionTime(), Types.TIMESTAMP);
                pStmt.setInt(5, entity.getAnswerable().getId());
                pStmt.setBoolean(6, entity.getExecuted());
                pStmt.setInt(7, entity.getCreator().getId());
                pStmt.setInt(8, entity.getVersion());
                pStmt.setObject(9, entity.getCreated(), Types.TIMESTAMP);
                pStmt.setObject(10, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final ITask entity) {
        executeStatement(new PreparedStatementAction<ITask>(String.format(
                "update %s set to_do=?, description=?, priority=?, execution_time=?, answerable_id=?, executed=?,"
                        + " creator_id=?, version=?, updated=? where id=?",
                getTableName()), true) {
            @Override
            public ITask doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getToDo());
                pStmt.setString(2, entity.getDescription());
                pStmt.setString(3, entity.getPriority().name());
                pStmt.setObject(4, entity.getExecutionTime(), Types.TIMESTAMP);
                pStmt.setInt(5, entity.getAnswerable().getId());
                pStmt.setBoolean(6, entity.getExecuted());
                pStmt.setInt(7, entity.getCreator().getId());
                pStmt.setInt(8, entity.getVersion());
                pStmt.setObject(9, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(10, entity.getId());

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
    protected ITask parseRow(final ResultSet resultSet) throws SQLException {
        final ITask entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setToDo(resultSet.getString("to_do"));
        entity.setDescription(resultSet.getString("description"));
        entity.setPriority(TaskPriority.valueOf(resultSet.getString("priority")));
        entity.setExecutionTime(resultSet.getTimestamp("execution_time"));

        final IUserAccount answerable = new UserAccount();
        answerable.setId((Integer) resultSet.getObject("answerable_id"));
        entity.setAnswerable(answerable);

        entity.setExecuted(resultSet.getBoolean("executed"));

        final IUserAccount creator = new UserAccount();
        creator.setId((Integer) resultSet.getObject("creator_id"));
        entity.setCreator(creator);

        entity.setVersion((Integer) resultSet.getObject("version"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "task";
    }

    @Override
    public List<ITask> find(final TaskFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final TaskFilter filter) {
        return executeCountQuery("");
    }
}
