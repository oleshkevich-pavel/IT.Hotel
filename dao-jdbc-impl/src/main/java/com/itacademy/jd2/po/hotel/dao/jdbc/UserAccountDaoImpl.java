package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.UserAccount;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class UserAccountDaoImpl extends AbstractDaoImpl<IUserAccount, Integer> implements IUserAccountDao {

    @Override
    public IUserAccount createEntity() {
        return new UserAccount();
    }

    @Override
    public void insert(final IUserAccount entity) {
        executeStatement(
                new PreparedStatementAction<IUserAccount>(
                        String.format(
                                "insert into %s (email, password, role, first_name, last_name, birthday, "
                                        + "address, phone, created, updated) values(?,?,?,?,?,?,?,?,?,?)",
                                getTableName()),
                        true) {
                    @Override
                    public IUserAccount doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                        pStmt.setString(1, entity.getEmail());
                        pStmt.setString(2, entity.getPassword());
                        pStmt.setString(3, entity.getRole().name());
                        pStmt.setString(4, entity.getFirstName());
                        pStmt.setString(5, entity.getLastName());
                        pStmt.setObject(6, entity.getBirthday(), Types.TIMESTAMP);
                        pStmt.setString(7, entity.getAddress());
                        pStmt.setString(8, entity.getPhone());
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
    public void update(final IUserAccount entity) {
        executeStatement(new PreparedStatementAction<IUserAccount>(
                String.format("update %s set email=?, password=?, role=?, first_name=?, last_name=?, birthday=?, "
                        + "address=?, phone=?, updated=? where id=?", getTableName()),
                true) {
            @Override
            public IUserAccount doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getEmail());
                pStmt.setString(2, entity.getPassword());
                pStmt.setString(3, entity.getRole().name());
                pStmt.setString(4, entity.getFirstName());
                pStmt.setString(5, entity.getLastName());
                pStmt.setObject(6, entity.getBirthday(), Types.TIMESTAMP);
                pStmt.setString(7, entity.getAddress());
                pStmt.setString(8, entity.getPhone());
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
    protected IUserAccount parseRow(final ResultSet resultSet) throws SQLException {
        final IUserAccount entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setEmail(resultSet.getString("email"));
        entity.setPassword(resultSet.getString("password"));
        entity.setRole(Role.valueOf(resultSet.getString("role")));
        entity.setFirstName(resultSet.getString("first_name"));
        entity.setLastName(resultSet.getString("last_name"));
        entity.setBirthday(resultSet.getTimestamp("birthday"));
        entity.setAddress(resultSet.getString("address"));
        entity.setPhone(resultSet.getString("phone"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "user_account";
    }

    @Override
    public List<IUserAccount> find(final UserAccountFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final UserAccountFilter filter) {
        return executeCountQuery("");
    }

    @Override
    public IUserAccount getByEmail(final String email) {
        throw new UnsupportedOperationException();
    }
}
