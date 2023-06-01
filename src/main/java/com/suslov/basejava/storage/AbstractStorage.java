package com.suslov.basejava.storage;

import com.suslov.basejava.exception.ExistStorageException;
import com.suslov.basejava.exception.NotExistStorageException;
import com.suslov.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void addResumeInStorage(SK searchKey, Resume resume);

    protected abstract Resume getResumeFromStorage(SK searchKey);

    protected abstract void updateResumeInStorage(SK searchKey, Resume resume);

    protected abstract void deleteResumeInStorage(SK searchKey);

    protected abstract List<Resume> getAllResumes();

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = receiveSearchKeyIfResumeNotExist(resume.getUuid());
        addResumeInStorage(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get resume by UUID '" + uuid + "'");
        SK searchKey = receiveSearchKeyIfResumeExist(uuid);
        return getResumeFromStorage(searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = receiveSearchKeyIfResumeExist(resume.getUuid());
        updateResumeInStorage(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete resume by UUID '" + uuid + "'");
        SK searchKey = receiveSearchKeyIfResumeExist(uuid);
        deleteResumeInStorage(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAllResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        LOG.info("Get all sorted resumes: \n" + resumes);
        return resumes;
    }

    private SK receiveSearchKeyIfResumeExist(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume by UUID '" + uuid + "' is not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK receiveSearchKeyIfResumeNotExist(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume by UUID '" + uuid + "' is already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
