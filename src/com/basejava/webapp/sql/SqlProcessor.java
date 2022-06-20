package com.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlProcessor<T> {
    T process(PreparedStatement ps) throws SQLException;
}
