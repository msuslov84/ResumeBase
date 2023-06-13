package com.suslov.basejava.storage.file.serializer;

import com.suslov.basejava.exception.SerializeException;
import com.suslov.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    void writeToFile(Resume resume, OutputStream out) throws SerializeException;

    Resume readFromFile(InputStream in) throws SerializeException;
}
