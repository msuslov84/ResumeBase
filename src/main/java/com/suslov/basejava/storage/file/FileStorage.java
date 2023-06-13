package com.suslov.basejava.storage.file;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;
import com.suslov.basejava.storage.file.serializer.Serializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private static final Logger LOG = Logger.getLogger(FileStorage.class.getName());

    private final File directory;
    private final Serializer serializer;

    public FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "Error: the file directory must not be null");
        if (!directory.isDirectory() || !directory.canRead() || !directory.canWrite()) {
            String errorMessage = "File storage '" + directory.getAbsolutePath() + "' is not a directory or is not readable/writable";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage);
        }
        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        for (File file : getStorageFiles()) {
            deleteResumeInStorage(file);
        }
    }

    @Override
    public int size() {
        return getStorageFiles().length;
    }

    @Override
    protected void addResumeInStorage(File file, Resume resume) {
        boolean fileCreated;
        String errorMessage = "Error creating file '" + file.getAbsolutePath() + "' for resume " + resume;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException exp) {
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, file.getName(), exp);
        }
        if (fileCreated) {
            updateResumeInStorage(file, resume);
        } else {
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, file.getName());
        }
    }

    @Override
    protected Resume getResumeFromStorage(File file) {
        try {
            return serializer.readFromFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (SerializeException | IOException exp) {
            String errorMessage = "Error getting resume from the file storage by path '" + file.getAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, exp);
        }
    }

    @Override
    protected void updateResumeInStorage(File file, Resume resume) {
        try {
            serializer.writeToFile(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (SerializeException | IOException exp) {
            String errorMessage = "Error updating resume " + resume + " in the file storage by path '" + file.getAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, resume.getUuid(), exp);
        }
    }

    @Override
    protected void deleteResumeInStorage(File file) {
        if (!file.delete()) {
            String errorMessage = "Error deleting resume in the file storage by path '" + file.getAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, file.getName());
        }
    }

    @Override
    protected List<Resume> getAllResumes() {
        return Arrays.stream(getStorageFiles()).map(this::getResumeFromStorage).collect(Collectors.toList());
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    private File[] getStorageFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            String errorMessage = "Invalid path directory of the file storage - '" + directory.getAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage);
        }
        return files;
    }
}
