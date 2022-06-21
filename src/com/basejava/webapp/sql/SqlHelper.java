package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T ExecuteAndProcessQuery(String query, SqlProcessor<T> sqlProcessor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlProcessor.process(ps);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(e);
            }
            throw new RuntimeException(e);
        }
    }
}
