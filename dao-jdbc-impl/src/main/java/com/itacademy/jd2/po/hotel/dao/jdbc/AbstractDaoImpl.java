package com.itacademy.jd2.po.hotel.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.itacademy.jd2.po.hotel.dao.api.IBaseDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.AbstractFilter;
import com.itacademy.jd2.po.hotel.dao.jdbc.exception.SQLExecutionException;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.PreparedStatementAction;
import com.itacademy.jd2.po.hotel.dao.jdbc.utils.StatementAction;

public abstract class AbstractDaoImpl<ENTITY, ID> implements IBaseDao<ENTITY, ID> {
    // логгер только для этого файла
    private static final Logger LOGGER = LoggerFactory.getLogger("abstractDAOLogger");

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @PostConstruct
    private void init() throws IllegalAccessException {
        // Class.forName("org.postgresql.Driver");
        if (url == null) {
            throw new IllegalAccessException("[url] can't be null");
        }
        if (password == null) {
            throw new IllegalAccessException("[password] can't be null");
        }
        if (user == null) {
            throw new IllegalAccessException("[user] can't be null");
        }
    }

    @Override
    public ENTITY get(final ID id) {
        return executeStatement(new StatementAction<ENTITY>() {
            @Override
            public ENTITY doWithStatement(final Statement statement) throws SQLException {
                statement.executeQuery("select * from " + getTableName() + " where id=" + id);

                final ResultSet resultSet = statement.getResultSet();
                final Set<String> columns = resolveColumnNames(resultSet);
                final boolean hasNext = resultSet.next();
                ENTITY result = null;
                if (hasNext) {
                    result = parseRow(resultSet, columns);
                }

                resultSet.close();
                return result;
            }
        });
    }

    @Override
    public List<ENTITY> selectAll() {
        return executeStatement(new StatementAction<List<ENTITY>>() {
            @Override
            public List<ENTITY> doWithStatement(final Statement statement) throws SQLException {
                statement.executeQuery("select * from " + getTableName());

                final ResultSet resultSet = statement.getResultSet();
                final Set<String> columns = resolveColumnNames(resultSet);
                final List<ENTITY> result = new ArrayList<>();
                boolean hasNext = resultSet.next();
                while (hasNext) {
                    result.add(parseRow(resultSet, columns));
                    hasNext = resultSet.next();
                }
                resultSet.close();

                return result;
            }
        });
    }

    @Override
    public void delete(final ID id) {
        executeStatement(
                new PreparedStatementAction<Integer>(String.format("delete from %s where id=?", getTableName())) {
                    @Override
                    public Integer doWithPreparedStatement(final PreparedStatement prepareStatement)
                            throws SQLException {
                        prepareStatement.setObject(1, id);
                        return prepareStatement.executeUpdate();
                    }
                });
    }

    @Override
    public void deleteAll() {
        executeStatement(new PreparedStatementAction<Integer>("delete from " + getTableName()) {
            @Override
            public Integer doWithPreparedStatement(final PreparedStatement prepareStatement) throws SQLException {
                return prepareStatement.executeUpdate();
            }
        });
    }

    protected <T> T executeStatement(final StatementAction<T> action) {
        try (Connection c = getConnection(); Statement stmt = c.createStatement()) {
            c.setAutoCommit(false);
            return action.doWithStatement(stmt);
        } catch (final SQLException e) {
            throw new SQLExecutionException(e); // wrap catchable exception with
                                                // runtime
        }
    }

    protected <T> T executeStatement(final PreparedStatementAction<T> action) {
        try (Connection c = getConnection();
                PreparedStatement pStmt = action.isReturnGeneratedKeys()
                        ? c.prepareStatement(action.getSql(), Statement.RETURN_GENERATED_KEYS)
                        : c.prepareStatement(action.getSql())) {
            c.setAutoCommit(false);
            try {
                final T doWithPreparedStatement = action.doWithPreparedStatement(pStmt);
                c.commit();
                return doWithPreparedStatement;
            } catch (final Exception e) {
                c.rollback();
                throw new RuntimeException(e);
            }

        } catch (final SQLException e) {
            throw new SQLExecutionException(e);
        }
    }

    protected void appendPaging(final AbstractFilter filter, final StringBuilder sql) {
        final Integer limit = filter.getLimit();
        final Integer offset = filter.getOffset();

        if (limit != null) {
            sql.append(" limit " + limit);
        }

        if (offset != null) {
            sql.append(" offset " + offset);
        }
    }

    protected void appendSort(final AbstractFilter filter, final StringBuilder sql) {
        final String sortColumn = filter.getSortColumn();
        final Boolean sortOrder = filter.getSortOrder();

        if (sortColumn != null) {
            sql.append(String.format(" order by %s ", sortColumn));

            if (sortOrder != null) {
                // sql.append(" ");
                sql.append(sortOrder ? "asc" : "desc");
            }
        }

    }

    protected List<ENTITY> executeFindQuery(final String sqlFragment) {
        return executeFindQueryWithCustomSelect(String.format("select * from %s %s", getTableName(), sqlFragment));
    }

    protected List<ENTITY> executeFindQueryWithCustomSelect(final String fullSql) {
        return executeStatement(new StatementAction<List<ENTITY>>() {
            @Override
            public List<ENTITY> doWithStatement(final Statement statement) throws SQLException {
                LOGGER.debug("execute select:" + fullSql);
                statement.executeQuery(fullSql);

                final ResultSet resultSet = statement.getResultSet();

                final Set<String> columns = resolveColumnNames(resultSet);

                final List<ENTITY> result = new ArrayList<>();
                boolean hasNext = resultSet.next();
                while (hasNext) {
                    result.add(parseRow(resultSet, columns));
                    hasNext = resultSet.next();
                }
                resultSet.close();
                return result;
            }

        });
    }

    protected long executeCountQuery(final String sql) {
        return executeStatement(new StatementAction<Long>() {
            @Override
            public Long doWithStatement(final Statement statement) throws SQLException {
                final String fullSql = String.format("select count(*) as total from %s %s", getTableName(), sql);
                LOGGER.debug("execute count:" + fullSql);

                statement.executeQuery(fullSql);

                final ResultSet resultSet = statement.getResultSet();
                resultSet.next();

                final long total = resultSet.getLong("total");
                resultSet.close();
                return total;
            }
        });
    }

    private Set<String> resolveColumnNames(final ResultSet resultSet) throws SQLException {
        final ResultSetMetaData rsMetaData = resultSet.getMetaData();
        final int numberOfColumns = rsMetaData.getColumnCount();
        final Set<String> columns = new HashSet<>();
        for (int i = 1; i <= numberOfColumns; i++) {
            columns.add(rsMetaData.getColumnName(i));
        }
        return columns;
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    protected ENTITY parseRow(final ResultSet resultSet) throws SQLException {
        throw new UnsupportedOperationException(
                "This method should be overriden in particular *Impl class or use alternative "
                        + "com.itacademy.jd2.po.hotel.dao.jdbc.AbstractDaoImpl.parseRow(ResultSet, List<String>)");
    };

    protected ENTITY parseRow(final ResultSet resultSet, final Set<String> columns) throws SQLException {
        // this method allows to specify in particular DAO the parser which
        // accepts list of columns. but by default it will fall back to
        // com.itacademy.jd2.po.hotel.dao.jdbc.AbstractDaoImpl.parseRow(ResultSet)
        return parseRow(resultSet);
    };

    @Override
    public ENTITY getFullInfo(final ID id) {
        final ENTITY room = get(id);
        return room;
    }

    @Override
    public List<ENTITY> getAllFullInfo() {
        final List<ENTITY> list = selectAll();
        return list;
    }

    protected abstract String getTableName();
}
