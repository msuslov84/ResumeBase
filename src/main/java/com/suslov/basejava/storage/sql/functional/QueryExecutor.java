package com.suslov.basejava.storage.sql.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryExecutor {

    void execute(PreparedStatement ps) throws SQLException;
}
