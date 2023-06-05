package com.suslov.basejava.storage.file;

import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;
import com.suslov.basejava.storage.file.serializer.Serializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serializer serializer;

    public FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!(directory.canRead() && directory.canWrite())) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
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
        String errorText = "Error creating file '" + file.getAbsolutePath() + "'";
        try {
            fileCreated = file.createNewFile();
        } catch (IOException exp) {
            throw new StorageException(errorText, file.getName(), exp);
        }
        if (fileCreated) {
            updateResumeInStorage(file, resume);
        } else {
            throw new StorageException(errorText, file.getName());
        }
    }

    @Override
    protected Resume getResumeFromStorage(File file) {
        try {
            return serializer.readFromFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException exp) {
            throw new StorageException("Error deserializing resume from file '" + file.getAbsolutePath() + "'", exp);
        }
    }

    @Override
    protected void updateResumeInStorage(File file, Resume resume) {
        try {
            serializer.writeToFile(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException exp) {
            throw new StorageException("Error serializing resume to file '" + file.getAbsolutePath() + "'",
                    resume.getUuid(), exp);
        }
    }

    @Override
    protected void deleteResumeInStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("Error deleting resume in file '" + file.getAbsolutePath() + "'", file.getName());
        }
    }

    @Override
    protected List<Resume> getAllResumes() {
        return Arrays.stream(getStorageFiles()).map(this::getResumeFromStorage).collect(Collectors.toList());
//        ArrayList<Resume> list = new ArrayList<>();
//        for (File file : getStorageFiles()) {
//            list.add(getResumeFromStorage(file));
//        }
//        return list;
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
            throw new StorageException("Invalid path directory", directory.getName());
        }
        return files;
    }
}
