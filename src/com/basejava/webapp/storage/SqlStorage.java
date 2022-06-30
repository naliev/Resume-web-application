package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(uuid) as count FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        HashMap<String, Resume> resumes = sqlHelper.execute("" +
                "SELECT trim(r.uuid) as uuid, r.full_name as full_name " +
                "FROM resume r " +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet resultSet = ps.executeQuery();
            HashMap<String, Resume> resumeMap = new HashMap<>();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                Resume r = new Resume(uuid,
                        resultSet.getString("full_name"));
                resumeMap.put(uuid,r);
            }
            return resumeMap;
        });
        sqlHelper.execute("SELECT type, value, trim(resume_uuid) AS resume_uuid from contract", ps -> {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("resume_uuid");
                insertContactIntoResume(resultSet, resumes.get(uuid));
            }
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "SELECT r.full_name, c.type, c.value " +
                "FROM resume r LEFT JOIN contract c on r.uuid = c.resume_uuid " +
                "WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, resultSet.getString("full_name"));
            do {
                insertContactIntoResume(resultSet, r);
            } while (resultSet.next());
            return r;
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("Delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE (uuid = ?)")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                deleteContractsFromDB(r);
                insertContactsIntoDB(conn, r);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                insertContactsIntoDB(conn, r);
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE (uuid = ?)", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    private void deleteContractsFromDB(Resume r) {
        sqlHelper.<Void>execute("DELETE FROM contract WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContactsIntoDB(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO contract (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> c : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, c.getKey().getTitle());
                ps.setString(3, c.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertContactIntoResume(ResultSet rs, Resume r) throws SQLException {
        if (rs.getString("type") != null) {
            r.addContact(ContactType.valueOf(rs.getString("type").toUpperCase()),
                    rs.getString("Value")
            );
        }
    }
}
