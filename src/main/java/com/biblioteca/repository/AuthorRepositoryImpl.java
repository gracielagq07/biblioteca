package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Author;

public class AuthorRepositoryImpl implements AuthorRepository {
    @Override
    public void addAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";

        try (
                Connection connection = DBManager.getConnection();
                PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            st.setString(1, author.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                author.setId(generatedId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir el autor: " + e.getMessage());
        }
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT id_author, name FROM authors ORDER BY name";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                authors.add(
                        new Author(
                                rs.getInt("id_author"),
                                rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar autores", e);
        }
        return authors;
    }

    @Override
    public void updateAuthor(Author author) {
        String sql = "UPDATE authors SET name = ? WHERE id_author = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getName());
            ps.setInt(2, author.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar autor", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM authors WHERE id_author = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar autor", e);
        }
    }

    @Override
    public List<Author> findByName(String name) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT id_author, name FROM authors WHERE name ILIKE ? ORDER BY name";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    authors.add(
                            new Author(
                                    rs.getInt("id_author"),
                                    rs.getString("name")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar autor", e);
        }

        return authors;
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM authors WHERE name = ? LIMIT 1";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar existencia del autor", e);
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM authors WHERE id_author = ? LIMIT 1";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar existencia del autor", e);
        }
    }
}
