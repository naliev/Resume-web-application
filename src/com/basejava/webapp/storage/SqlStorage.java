package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.ExecuteAndProcessQuery("SELECT COUNT(uuid) as count FROM resume", ps -> {
            try {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.ExecuteAndProcessQuery("SELECT trim(uuid) as uuid, full_name FROM resume ORDER BY uuid DESC", ps -> {
            ResultSet resultSet = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (resultSet.next()) {
                resumeList.add(new Resume(resultSet.getString("uuid"),
                        resultSet.getString("full_name")));
            }
            return resumeList;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.ExecuteAndProcessQuery("SELECT full_name FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Resume(uuid, resultSet.getString("full_name"));
            } else {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.ExecuteAndProcessQuery("Delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.ExecuteAndProcessQuery("UPDATE resume SET full_name = ? WHERE (uuid = ?)", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.<Boolean>ExecuteAndProcessQuery("INSERT INTO resume VALUES (?, ?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return true;
            });
        } catch (Exception e) {
            throw new ExistStorageException(r.getUuid());
        }

    }

    @Override
    public void delete(String uuid) {
        sqlHelper.ExecuteAndProcessQuery("DELETE FROM resume WHERE (uuid = ?)", ps -> {
            ps.setString(1, uuid);
            ps.executeUpdate();
            return null;
        });
    }
}
