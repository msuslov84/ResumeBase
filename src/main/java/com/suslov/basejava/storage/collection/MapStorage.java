package com.suslov.basejava.storage.collection;

import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void addResumeInStorage(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume getResumeFromStorage(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateResumeInStorage(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void deleteResumeInStorage(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }
}