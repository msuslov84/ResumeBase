package com.suslov.basejava.storage.file;

import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.AbstractStorage;
import com.suslov.basejava.storage.file.serializer.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    public PathStorage(String dir, Serializer serializer) {
        this.serializer = serializer;
        this.directory = Paths.get(Objects.requireNonNull(dir, "Path storage directory must not be null"));
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new StorageException("Path storage '" + directory + "' is not a directory or is not readable/writable");
        }
    }

    @Override
    public void clear() {
        getStoragePaths().forEach(this::deleteResumeInStorage);
    }

    @Override
    public int size() {
        return (int) getStoragePaths().count();
    }

    @Override
    protected void addResumeInStorage(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException exp) {
            throw new StorageException("Error creating file by path '" + path.toAbsolutePath() + "'",
                    getFileName(path), exp);
        }
        updateResumeInStorage(path, resume);
    }

    @Override
    protected Resume getResumeFromStorage(Path path) {
        try {
            return serializer.readFromFile(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IllegalArgumentException | IOException exp) {
            throw new StorageException("Error deserializing resume by path '" + path.toAbsolutePath() + "'", exp);
        }
    }

    @Override
    protected void updateResumeInStorage(Path path, Resume resume) {
        try {
            serializer.writeToFile(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException exp) {
            throw new StorageException("Error serializing resume by path '" + path.toAbsolutePath() + "'",
                    resume.getUuid(), exp);
        }
    }

    @Override
    protected void deleteResumeInStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException exp) {
            throw new StorageException("Error deleting resume by path '" + path.toAbsolutePath() + "'",
                    getFileName(path), exp);
        }
    }

    @Override
    protected List<Resume> getAllResumes() {
        return getStoragePaths().map(this::getResumeFromStorage).collect(Collectors.toList());
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    private Stream<Path> getStoragePaths() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error getting files from storage by path '" + directory.toAbsolutePath() + "'",
                    directory.toString(), e);
        }
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
