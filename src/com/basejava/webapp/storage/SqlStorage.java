package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.executeAndProcessQuery("SELECT COUNT(uuid) as count FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeAndProcessQuery("SELECT trim(uuid) as uuid, full_name" +
                                                " FROM resume ORDER BY full_name, uuid DESC", ps -> {
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
        return sqlHelper.executeAndProcessQuery("SELECT full_name FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void clear() {
        sqlHelper.executeAndProcessQuery("Delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.executeAndProcessQuery("UPDATE resume SET full_name = ? WHERE (uuid = ?)", ps -> {
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
        sqlHelper.<Boolean>executeAndProcessQuery("INSERT INTO resume VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return true;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>executeAndProcessQuery("DELETE FROM resume WHERE (uuid = ?)", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }
}
