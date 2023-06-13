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
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private static final Logger LOG = Logger.getLogger(PathStorage.class.getName());

    private final Path directory;
    private final Serializer serializer;

    public PathStorage(String dir, Serializer serializer) {
        this.serializer = serializer;
        this.directory = Paths.get(Objects.requireNonNull(dir, "Error: the path storage directory must not be null"));
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) || !Files.isReadable(directory)) {
            String errorMessage = "Path storage '" + directory.toAbsolutePath() + "' is not a directory or is not readable/writable";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage);
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
            String errorMessage = "Error creating file by path '" + path.toAbsolutePath() + "' for resume " + resume;
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, getFileName(path), exp);
        }
        updateResumeInStorage(path, resume);
    }

    @Override
    protected Resume getResumeFromStorage(Path path) {
        try {
            return serializer.readFromFile(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IllegalArgumentException | IOException exp) {
            String errorMessage = "Error getting resume from the path storage by path '" + path.toAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, exp);
        }
    }

    @Override
    protected void updateResumeInStorage(Path path, Resume resume) {
        try {
            serializer.writeToFile(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException exp) {
            String errorMessage = "Error updating resume " + resume + " in the path storage by path '" + path.toAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, resume.getUuid(), exp);
        }
    }

    @Override
    protected void deleteResumeInStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException exp) {
            String errorMessage = "Error deleting resume in the path storage by path '" + path.toAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, getFileName(path), exp);
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
            String errorMessage = "Error getting all files from the path storage by path '" + directory.toAbsolutePath() + "'";
            LOG.warning(errorMessage);
            throw new StorageException(errorMessage, directory.toString(), e);
        }
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
