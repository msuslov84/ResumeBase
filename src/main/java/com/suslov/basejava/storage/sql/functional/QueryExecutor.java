package com.suslov.basejava.storage.sql.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryExecutor<T> {

    T execute(PreparedStatement ps) throws SQLException;
}
