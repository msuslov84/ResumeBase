package com.suslov.basejava.storage.sql.functional;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionReceiver<T> {

    T receive(Connection connect) throws SQLException;
}
