package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.ICommentDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Comment;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Room;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class CommentDaoImpl extends AbstractDaoImpl<IComment, Integer> implements ICommentDao {

    @Override
    public IComment createEntity() {
        return new Comment();
    }

    @Override
    public void insert(final IComment entity) {
        executeStatement(new PreparedStatementAction<IComment>(
                String.format("insert into %s (user_account_id, room_id, comment, created, updated) values(?,?,?,?,?)",
                        getTableName()),
                true) {
            @Override
            public IComment doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getUserAccount().getId());
                pStmt.setInt(2, entity.getRoom().getId());
                pStmt.setString(3, entity.getComment());
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
    public void update(final IComment entity) {
        executeStatement(new PreparedStatementAction<IComment>(String.format(
                "update %s set user_account_id=?, room_id=?, comment=?, updated=? where id=?", getTableName()), true) {
            @Override
            public IComment doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getUserAccount().getId());
                pStmt.setInt(2, entity.getRoom().getId());
                pStmt.setString(3, entity.getComment());
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
    protected IComment parseRow(final ResultSet resultSet) throws SQLException {
        final IComment entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));

        final IUserAccount userAccount = new UserAccount();
        userAccount.setId((Integer) resultSet.getObject("user_account_id"));
        entity.setUserAccount(userAccount);

        final IRoom room = new Room();
        room.setId((Integer) resultSet.getObject("room_id"));
        entity.setRoom(room);

        entity.setComment(resultSet.getString("comment"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "comment";
    }

    @Override
    public List<IComment> find(final CommentFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final CommentFilter filter) {
        return executeCountQuery("");
    }
}
