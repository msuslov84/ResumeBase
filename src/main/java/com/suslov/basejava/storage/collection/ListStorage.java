package com.suslov.basejava.storage.collection;

import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.removeAll(new ArrayList<>(storage));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void addResumeInStorage(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResumeFromStorage(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void updateResumeInStorage(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResumeInStorage(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return storage.indexOf(resume);
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}