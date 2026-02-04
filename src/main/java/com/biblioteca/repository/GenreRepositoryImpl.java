package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Genre;

public class GenreRepositoryImpl implements GenreRepository {
    @Override
    public void addGenre(Genre genre){
        String sql="INSERT INTO genres (genre) VALUES (?)";
        try(
            Connection connection = DBManager.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);){
            st.setString(1, genre.getGenre());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir el género literario" + e.getMessage());
        }
        
    }

}
