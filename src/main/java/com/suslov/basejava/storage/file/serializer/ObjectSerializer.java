package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.model.Resume;

import java.io.*;

public class ObjectSerializer implements Serializer {

    @Override
    public void writeToFile(Resume resume, OutputStream out) throws SerializeException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(resume);
        } catch (IOException exp) {
            throw new SerializeException("Error object serialization to file", exp);
        }
    }

    @Override
    public Resume readFromFile(InputStream in) throws SerializeException {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            throw new SerializeException("Error object deserialization from file", exp);
        }
    }
}
