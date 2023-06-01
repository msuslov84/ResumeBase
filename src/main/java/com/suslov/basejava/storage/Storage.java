package com.suslov.basejava.storage;

import com.suslov.basejava.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
