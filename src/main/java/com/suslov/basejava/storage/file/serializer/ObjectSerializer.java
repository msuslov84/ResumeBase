package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.model.Resume;

import java.io.*;

public class ObjectSerializer implements Serializer {

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException exp) {
            throw new SerializeException("Error reading resume from file", exp);
        }
    }
}
