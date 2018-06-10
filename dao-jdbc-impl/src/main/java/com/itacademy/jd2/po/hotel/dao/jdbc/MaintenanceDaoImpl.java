package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.itacademy.jd2.po.hotel.dao.api.IMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.jdbc.model.Maintenance;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;

@Repository
public class MaintenanceDaoImpl extends AbstractDaoImpl<IMaintenance, Integer> implements IMaintenanceDao {

    @Override
    public IMaintenance createEntity() {
        return new Maintenance();
    }

    @Override
    public void insert(final IMaintenance entity) {
        executeStatement(new PreparedStatementAction<IMaintenance>(String.format(
                "insert into %s (name, actual_price, photo_link, available, created, updated) values(?,?,?,?,?,?)",
                getTableName()), true) {
            @Override
            public IMaintenance doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setDouble(2, entity.getActualPrice());
                pStmt.setString(3, entity.getPhotoLink());
                pStmt.setBoolean(4, entity.isAvailable());
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
    public void update(final IMaintenance entity) {
        executeStatement(new PreparedStatementAction<IMaintenance>(
                String.format("update %s set name=?, actual_price=?, photo_link=?, available=?, updated=? where id=?",
                        getTableName()),
                true) {
            @Override
            public IMaintenance doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
                pStmt.setString(1, entity.getName());
                pStmt.setDouble(2, entity.getActualPrice());
                pStmt.setString(3, entity.getPhotoLink());
                pStmt.setBoolean(4, entity.isAvailable());
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
    protected String getTableName() {
        return "maintenance";
    }

    @Override
    protected IMaintenance parseRow(final ResultSet resultSet) throws SQLException {
        final IMaintenance entity = createEntity();
        entity.setId((Integer) resultSet.getObject("id"));
        entity.setName(resultSet.getString("name"));
        entity.setActualPrice(resultSet.getDouble("actual_price"));
        entity.setName(resultSet.getString("photo_link"));
        entity.setAvailable(resultSet.getBoolean("available"));
        entity.setCreated(resultSet.getTimestamp("created"));
        entity.setUpdated(resultSet.getTimestamp("updated"));
        return entity;
    }

    @Override
    public List<IMaintenance> find(final MaintenanceFilter filter) {
        final StringBuilder sql = new StringBuilder("");
        appendWHEREs(sql, filter);
        appendSort(filter, sql);
        appendPaging(filter, sql);
        return executeFindQuery(sql.toString());
    }

    @Override
    public long getCount(final MaintenanceFilter filter) {
        return executeCountQuery("");
    }

    private void appendWHEREs(final StringBuilder sb, final MaintenanceFilter filter) {
        // TODO ANDs + ORs
        final List<String> ands = new ArrayList<String>();
        if (StringUtils.isNotBlank(filter.getName())) {
            ands.add(String.format("name='%s'", filter.getName()));
        }

        if (filter.getAvailable() != null) {
            ands.add(String.format("available='%s'", filter.getAvailable()));
        }

        final Iterator<String> andsIter = ands.iterator();
        if (andsIter.hasNext()) {
            final String firtsCondition = andsIter.next();

            sb.append(String.format("where %s", firtsCondition));

            while (andsIter.hasNext()) {
                final String condition = andsIter.next();
                sb.append(String.format(" and %s", condition));
            }
        }
    }

    @Override
    public Double getMaxPrice() {
        throw new UnsupportedOperationException();
    }
}
