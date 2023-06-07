package com.suslov.basejava.storage.sql;

import com.suslov.basejava.exception.NotExistStorageException;
import com.suslov.basejava.model.ContactType;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.model.section.AbstractSection;
import com.suslov.basejava.model.section.SectionType;
import com.suslov.basejava.storage.Storage;
import com.suslov.basejava.util.parser.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        sqlHelper.executeQuery("DELETE FROM resume WHERE TRUE", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(connect -> insertResume(resume, connect));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.receiveInTransaction(connect -> getResume(uuid, connect));
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeTransaction(connect -> updateResume(resume, connect));
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid=?", ps -> deleteResume(uuid, ps));
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.receiveInTransaction(this::getAllSortedResumes);
    }

    @Override
    public int size() {
        return sqlHelper.receiveResult("SELECT COUNT(*) AS count_rows FROM resume", this::countResumes);
    }

    private void insertResume(Resume resume, Connection connect) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        }
        insertContacts(resume, connect);
//        insertSections(resume, connect);
    }

    private Resume getResume(String uuid, Connection connect) throws SQLException {
        String queryText = "SELECT r.uuid, r.full_name, c.type AS cType, c.value AS cValue FROM resume AS r " +
                "LEFT JOIN contact AS c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid=? ";

        try (PreparedStatement ps = connect.prepareStatement(queryText)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(resume, rs);
                //addSection(resume, rs);
            } while (rs.next());
            return resume;
        }
    }

    private void addContact(Resume resume, ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("cType");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), resultSet.getString("cValue"));
        }
    }

    private void addSection(Resume resume, ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("sType");
        String value = resultSet.getString("sValue");
        if (type != null && value != null) {
            resume.addSection(SectionType.valueOf(type), JsonParser.read(value, AbstractSection.class));
        }
    }

    private void updateResume(Resume resume, Connection connect) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
            String uuid = resume.getUuid();
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        }
        deleteContacts(resume, connect);
//        deleteSections(resume, connect);
        insertContacts(resume, connect);
//        insertSections(resume, connect);
    }

    private void deleteContacts(Resume resume, Connection connect) throws SQLException {
        deleteFromResume(resume, connect, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Resume resume, Connection connect) throws SQLException {
//        deleteFromResume(resume, connect, "DELETE FROM section WHERE resume_uuid=?");
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

    private void deleteFromResume(Resume resume, Connection connect, String queryText) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement(queryText)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void getSections(Map<String, Resume> resumes, Connection connect) throws SQLException {
//        try (PreparedStatement ps = connect.prepareStatement("SELECT * FROM section s")) {
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                setSections(resumes.get(resultSet.getString("resume_uuid")), resultSet);
//            }
//        }
    }

    private void deleteResume(String uuid, PreparedStatement ps) throws SQLException {
        ps.setString(1, uuid);
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private List<Resume> getAllSortedResumes(Connection connect) throws SQLException {
        String queryText = "SELECT r.uuid, r.full_name, c.type AS cType, c.value AS cValue FROM resume AS r " +
                "LEFT JOIN contact AS c " +
                "ON r.uuid = c.resume_uuid " +
                "ORDER BY full_name, uuid";

        List<Resume> resumes = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(queryText)) {
            ResultSet rs = ps.executeQuery();
            Resume resume = null;
            while (rs.next()) {
                if (resume == null || !resume.getUuid().equals(rs.getString("uuid"))) {
                    resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    resumes.add(resume);
                }
                addContact(resume, rs);
                //addSection(resume, rs);
            }
        }
        return resumes;
    }

    private int countResumes(PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getInt("count_rows");
    }
}
