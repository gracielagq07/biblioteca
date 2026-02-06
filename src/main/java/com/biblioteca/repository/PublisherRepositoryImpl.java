package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Publisher;

public class PublisherRepositoryImpl implements PublisherRepository {

    @Override
    public void addPublisher(Publisher publisher) {
        String sql = "INSERT INTO publishers (name) VALUES (?)";

        try (
                Connection connection = DBManager.getConnection();
                PreparedStatement st = connection.prepareStatement(sql);) {
            st.setString(1, publisher.getName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir la editorial" + e.getMessage());
        }
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        String sql = "SELECT id_publisher, name FROM publishers ORDER BY name";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                publishers.add(new Publisher(
                        rs.getInt("id_publisher"),
                        rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar editoriales", e);
        }

        return publishers;
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        String sql = "UPDATE publishers SET name = ? WHERE id_publisher = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, publisher.getName());
            ps.setInt(2, publisher.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar editorial", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM publishers WHERE id_publisher = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar editorial", e);
        }
    }

    @Override
    public List<Publisher> findByName(String name) {
        List<Publisher> publishers = new ArrayList<>();
        String sql = "SELECT id_publisher, name FROM publishers WHERE name ILIKE ? ORDER BY name";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    publishers.add(new Publisher(
                            rs.getInt("id_publisher"),
                            rs.getString("name")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar editorial", e);
        }

        return publishers;
    }

}
