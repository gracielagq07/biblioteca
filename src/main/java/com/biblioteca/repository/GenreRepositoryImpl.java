package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Genre;

public class GenreRepositoryImpl implements GenreRepository {
    @Override
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO genres (genre) VALUES (?)";
        try (
                Connection connection = DBManager.getConnection();
                PreparedStatement st = connection.prepareStatement(sql);) {
            st.setString(1, genre.getGenre());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir el género literario" + e.getMessage());
        }

    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT id_genre, genre FROM genres ORDER BY genre";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt("id_genre"),
                        rs.getString("genre")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar géneros", e);
        }

        return genres;
    }

    @Override
    public void updateGenre(Genre genre) {
        String sql = "UPDATE genres SET genre = ? WHERE id_genre = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, genre.getGenre());
            ps.setInt(2, genre.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar género", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM genres WHERE id_genre = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar género", e);
        }
    }

    @Override
    public List<Genre> findByName(String genreName) {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT id_genre, genre FROM genres WHERE genre ILIKE ? ORDER BY genre";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + genreName + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt("id_genre"),
                            rs.getString("genre")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar género", e);
        }

        return genres;
    }

}
