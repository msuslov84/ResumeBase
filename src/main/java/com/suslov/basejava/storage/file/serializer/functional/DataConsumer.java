package com.suslov.basejava.storage.file.serializer.functional;

import java.io.IOException;

@FunctionalInterface
public interface DataConsumer<T> {

    void accept(T t) throws IOException;
}
