package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

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
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> resumes = new HashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT trim(r.uuid) as uuid, r.full_name as full_name " +
                    "FROM resume r " +
                    "ORDER BY full_name, uuid")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    Resume r = new Resume(uuid, resultSet.getString("full_name"));
                    resumes.put(uuid, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT type, value, trim(resume_uuid) AS resume_uuid from contract")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    insertContactIntoResume(resultSet, resumes.get(uuid));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT type, value, trim(resume_uuid) AS resume_uuid from section")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    insertSectionIntoResume(resultSet, resumes.get(uuid));
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, resultSet.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contract WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    insertContactIntoResume(resultSet, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    insertSectionIntoResume(resultSet, r);
                }
            }

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
                deleteContractsFromDB(conn, r);
                insertContactsIntoDB(conn, r);
                deleteSectionsFromDB(conn, r);
                insertSectionsIntoDB(conn, r);
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
                insertSectionsIntoDB(conn, r);
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

    private void deleteAttribute(String sql, Connection conn, String... parameters) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setString(i + 1, parameters[i]);
            }
            ps.execute();
        }
    }

    private void deleteContractsFromDB(Connection conn, Resume r) throws SQLException {
        deleteAttribute("DELETE FROM contract WHERE resume_uuid=?", conn, r.getUuid());
    }

    private void deleteSectionsFromDB(Connection conn, Resume r) throws SQLException {
        deleteAttribute("DELETE FROM section WHERE resume_uuid=?", conn, r.getUuid());
    }

    private void insertContactIntoResume(ResultSet rs, Resume r) throws SQLException {
        if (rs.getString("type") != null) {
            r.addContact(
                    ContactType.valueOf(rs.getString("type").toUpperCase()),
                    rs.getString("Value")
            );
        }
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

    private void insertSectionsIntoDB(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entries : r.getSections().entrySet()) {
                SectionType type = entries.getKey();
                ps.setString(1, type.toString());
                ps.setString(2, JsonParser.write(entries.getValue(), AbstractSection.class));
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSectionIntoResume(ResultSet rs, Resume r) throws SQLException {
        if (rs.getString("type") != null) {
            String sectionValue = rs.getString("Value");
            r.addSection(
                    SectionType.valueOf(rs.getString("type").toUpperCase()),
                    JsonParser.read(sectionValue, AbstractSection.class));
        }
    }
}
