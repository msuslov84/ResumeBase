package com.suslov.basejava.storage.sql.functional;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionExecutor<T> {

    T execute(Connection connect) throws SQLException;
}
