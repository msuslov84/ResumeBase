package com.suslov.basejava.storage.file.serializer.functional;

import java.io.IOException;

@FunctionalInterface
public interface DataFunction {

    void set() throws IOException;
}
