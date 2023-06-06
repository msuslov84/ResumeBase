package com.suslov.basejava.storage.sql;

import com.suslov.basejava.exception.NotExistStorageException;
import com.suslov.basejava.model.ContactType;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// AbstractStorage class isn't needed to implement SQL, because SQL takes over some of its functionality
// (in particular, uniqueness control)
public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume WHERE TRUE");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>executeTransaction(connect -> {
            try (PreparedStatement ps = connect.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, connect);
//            insertSections(resume, connect);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(connect -> {
            Resume resume = getResume(uuid, connect);
            setResumeContacts(resume, connect);
            return resume;
        });
//        try (PreparedStatement ps = connect.prepareStatement("" +
//                    "SELECT * FROM section s WHERE resume_uuid=?")) {
//                ps.setString(1, uuid);
//                ResultSet resultSet = ps.executeQuery();
//                while (resultSet.next()) {
//                    setSections(resume, resultSet);
//                }
//            }
//
//            return resume;
//        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>executeTransaction(connect -> {
            try (PreparedStatement ps = connect.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                String uuid = resume.getUuid();
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(resume, connect);
            insertContacts(resume, connect);
//            deleteSections(resume, connect);
//            insertSections(resume, connect);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>executeQuery("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeTransaction(connect -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            String queryText = "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid";
            try (PreparedStatement ps = connect.prepareStatement(queryText)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }
            setAllContacts(resumes, connect);
//            getSections(resumes, connect);

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT COUNT(*) AS count_rows FROM resume",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    resultSet.next();
                    return resultSet.getInt("count_rows");
                });
    }

    private void insertContacts(Resume resume, Connection connect) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume, Connection connect) throws SQLException {
        deleteFromResume(resume, connect, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private Resume getResume(String uuid, Connection connect) throws SQLException {
        String queryText = "SELECT uuid, full_name FROM resume WHERE uuid=?";
        try (PreparedStatement ps = connect.prepareStatement(queryText)) {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        }
    }

    private void setResumeContacts(Resume resume, Connection connect) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                addContact(resume, resultSet);
            }
        }
    }

    private void setAllContacts(Map<String, Resume> resumes, Connection connect) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("SELECT * FROM contact")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                addContact(resumes.get(resultSet.getString("resume_uuid")), resultSet);
            }
        }
    }

    private void addContact(Resume resume, ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), resultSet.getString("value"));
        }
    }

    private void insertSections(Resume resume, Connection connect) throws SQLException {
//        try (PreparedStatement ps = connect.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
//            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
//                SectionType sectionType = section.getKey();
//
//                ps.setString(1, resume.getUuid());
//                ps.setString(2, sectionType.name());
//                ps.setString(3, JsonParser.write(section.getValue(), AbstractSection.class));
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        }
    }

    private void deleteSections(Resume resume, Connection connect) throws SQLException {
//        deleteFromResume(resume, connect, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void setSections(Resume resume, ResultSet resultSet) throws SQLException {
//        String type = resultSet.getString("type");
//        String value = resultSet.getString("value");
//        if (type != null && value != null) {
//            resume.setSection(SectionType.valueOf(type), JsonParser.read(value, AbstractSection.class));
//        }
    }

    private void getSections(Map<String, Resume> resumes, Connection connect) throws SQLException {
//        try (PreparedStatement ps = connect.prepareStatement("SELECT * FROM section s")) {
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                setSections(resumes.get(resultSet.getString("resume_uuid")), resultSet);
//            }
//        }
    }

    private void deleteFromResume(Resume resume, Connection connect, String queryText) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement(queryText)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
