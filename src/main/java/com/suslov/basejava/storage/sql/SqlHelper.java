package com.suslov.basejava.storage.sql;

import com.suslov.basejava.exception.ExistStorageException;
import com.suslov.basejava.exception.StorageException;
import com.suslov.basejava.storage.sql.functional.ConnectionFactory;
import com.suslov.basejava.storage.sql.functional.QueryExecutor;
import com.suslov.basejava.storage.sql.functional.TransactionExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory factory;

    protected SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void executeQuery(String queryText) {
        executeQuery(queryText, PreparedStatement::execute);
    }

    public <T> T executeQuery(String queryText, QueryExecutor<T> function) {
        try (PreparedStatement ps = factory.getConnection().prepareStatement(queryText)) {
            return function.execute(ps);
        } catch (SQLException exp) {
            exceptionHandle(exp);
            return null;
        }
    }

    public <T> T executeTransaction(TransactionExecutor<T> function) {
        try (Connection connection = factory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = function.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException exp) {
                connection.rollback();
                exceptionHandle(exp);
                return null;
            }
        } catch (IllegalArgumentException | SQLException exp) {
            throw new StorageException("Error of execute SQL query in transaction to database", exp);
        }
    }

    private void exceptionHandle(SQLException exp) throws StorageException {
        // Check Postgres error code for the changing exception type
        // (https://www.postgresql.org/docs/15/errcodes-appendix.html)
        if (exp.getSQLState().equals("23505")) {
            // Duplicate key error, unique constraint violation
            throw new ExistStorageException(exp);
        } else {
            throw new StorageException("Database query execution error", exp);
        }
    }
}
