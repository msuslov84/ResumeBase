package com.suslov.basejava.storage.array;

import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insertResume(Resume resume, Integer index);

    @Override
    public void clear() {
        // Присваиваем всем элементам хранилища с резюме null, обнуляем счетчик резюме
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
            throw new StorageException("Storage overflow", resume.getUuid());
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
            // Если резюме было найдено и удалено, смещаем все последующие резюме хранилища влево
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
            // Очищаем последнее резюме хранилища после его смещения влево
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
        return index != null && index >= 0;
    }
}
