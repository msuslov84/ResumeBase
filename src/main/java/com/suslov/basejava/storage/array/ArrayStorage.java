package com.suslov.basejava.storage.array;

import com.suslov.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume, Integer insertionPoint) {
        storage[size] = resume;
    }
}
