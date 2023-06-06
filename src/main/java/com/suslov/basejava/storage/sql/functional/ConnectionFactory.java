package com.suslov.basejava.storage.sql.functional;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionFactory {

    Connection getConnection() throws SQLException;
}
