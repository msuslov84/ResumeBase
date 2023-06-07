package com.suslov.basejava.storage.sql.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryReceiver<T> {

    T receive(PreparedStatement ps) throws SQLException;
}
