package com.itacademy.jd2.po.hotel.dao.jdbc.utils;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementAction<RETURN_TYPE> {

    RETURN_TYPE doWithStatement(Statement stmt) throws SQLException;

}
