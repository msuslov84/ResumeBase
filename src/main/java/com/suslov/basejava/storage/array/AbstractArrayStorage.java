package com.suslov.basejava.storage.array;

import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    private static final Logger LOG = Logger.getLogger(AbstractArrayStorage.class.getName());

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insertResume(Resume resume, Integer index);

    @Override
    public void clear() {
        LOG.info("Clear resume storage. Deleted " + size + " resumes");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void addResumeInStorage(Integer index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            LOG.warning("Storage size in " + size + " elements is full. Resume by UUID '" + uuid + "' cannot " +
                    "be placed in it");
            throw new StorageException("Storage overflow. Limit in " + size + " elements exhausted", uuid);
        }
        insertResume(resume, index);
        size++;
    }

    @Override
    protected Resume getResumeFromStorage(Integer index) {
        return storage[index];
    }

    @Override
    protected void updateResumeInStorage(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void deleteResumeInStorage(Integer index) {
        storage[index] = null;
        if (size - (index + 1) >= 0) {
            // Shift all subsequent repository resumes to the left and clear last one
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
            storage[size - 1] = null;
        }
        size--;
    }

    @Override
    protected List<Resume> getAllResumes() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}
