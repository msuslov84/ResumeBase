package com.suslov.basejava.storage;

import com.suslov.basejava.ResumeTestData;
import com.suslov.basejava.exception.ExistStorageException;
import com.suslov.basejava.exception.NotExistStorageException;
import com.suslov.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\Java\\Projects\\JavaOnlineProjects\\ResumeBase\\storage");

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = ResumeTestData.create(UUID_1, "Name1");
    private static final Resume RESUME_2 = ResumeTestData.create(UUID_2, "Name2");
    private static final Resume RESUME_3 = ResumeTestData.create(UUID_3, "Name3");
    private static final Resume RESUME_4 = ResumeTestData.create(UUID_4, "Name4");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size() {
        assertExpectedSize(3);
    }

    @Test
    void clear() {
        storage.clear();
        assertExpectedSize(0);
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertExpectedSize(4);
        assertSavedResume(RESUME_4);
    }

    @Test
    void saveExistingResume() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertExpectedSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void get() {
        assertSavedResume(RESUME_1);
        assertSavedResume(RESUME_2);
        assertSavedResume(RESUME_3);
    }

    @Test
    void getNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    private void assertSavedResume(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertExpectedSize(int expectedSize) {
        assertEquals(expectedSize, storage.size());
    }
}