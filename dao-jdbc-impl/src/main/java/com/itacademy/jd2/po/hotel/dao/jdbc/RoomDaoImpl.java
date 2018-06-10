package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IRoomDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Room;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class RoomDaoImpl extends AbstractDaoImpl<IRoom, Integer> implements IRoomDao {

    @Override
    public IRoom createEntity() {
        return new Room();
    }

    @Override
    public void insert(final IRoom entity) {
        executeStatement(
                new PreparedStatementAction<IRoom>(String.format(
                        "insert into %s (number, floor, type, accomodation, view, actual_price, "
                        + "desription, dirty, broken, created, updated) values(?,?,?,?,?,?,?,?,?,?,?)",
                        getTableName()), true) {
                    @Override
                    public IRoom doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                        pStmt.setInt(1, entity.getNumber());
                        pStmt.setInt(2, entity.getFloor());
                        pStmt.setString(3, entity.getType().name());
                        pStmt.setString(4, entity.getAccomodation().name());
                        pStmt.setString(5, entity.getView().name());
                        pStmt.setDouble(6, entity.getActualPrice());
                        pStmt.setString(7, entity.getDescription());
                        pStmt.setBoolean(8, entity.isDirty());
                        pStmt.setBoolean(9, entity.isBroken());
                        pStmt.setObject(10, entity.getCreated(), Types.TIMESTAMP);
                        pStmt.setObject(11, entity.getUpdated(), Types.TIMESTAMP);

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
    public void update(final IRoom entity) {
        executeStatement(new PreparedStatementAction<IRoom>(
                String.format("update %s set number=?, floor=?, type=?, accomodation=?, view=?, actual_price=?, "
                        + "description=?, dirty=?, broken=?, updated=? where id=?", getTableName()),
                true) {
            @Override
            public IRoom doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setInt(1, entity.getNumber());
                pStmt.setInt(2, entity.getFloor());
                pStmt.setString(3, entity.getType().name());
                pStmt.setString(4, entity.getAccomodation().name());
                pStmt.setString(5, entity.getView().name());
                pStmt.setDouble(6, entity.getActualPrice());
                pStmt.setString(7, entity.getDescription());
                pStmt.setBoolean(8, entity.isDirty());
                pStmt.setBoolean(9, entity.isBroken());
                pStmt.setObject(10, entity.getUpdated(), Types.TIMESTAMP);
                pStmt.setInt(11, entity.getId());

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
    protected IRoom parseRow(final ResultSet resultSet) throws SQLException {
        final IRoom entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setNumber((Integer) resultSet.getObject("number"));
        entity.setFloor((Integer) resultSet.getObject("floor"));
        entity.setType(RoomType.valueOf(resultSet.getString("type")));
        entity.setAccomodation(Accomodation.valueOf(resultSet.getString("accomodation")));
        entity.setView(ViewType.valueOf(resultSet.getString("view")));
        entity.setActualPrice(resultSet.getDouble("actual_price"));
        entity.setDescription(resultSet.getString("description"));
        entity.setDirty(resultSet.getBoolean("dirty"));
        entity.setBroken(resultSet.getBoolean("broken"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));

        return entity;
    }

    @Override
    protected String getTableName() {
        return "room";
    }

    @Override
    public List<IRoom> find(final RoomFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final RoomFilter filter) {
        return executeCountQuery("");
    }

    @Override
    public List<IRoom> findFreeRooms(final RoomFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double getMaxPrice() {
       // select max(actual_price) from room
        throw new UnsupportedOperationException();
    }
}
