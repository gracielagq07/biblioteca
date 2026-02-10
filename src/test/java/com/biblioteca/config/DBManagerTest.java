package com.biblioteca.config;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class DBManagerTest {

    @Test
    public void shouldConnectToDB() {
        try (Connection conn = DBManager.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            fail("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}