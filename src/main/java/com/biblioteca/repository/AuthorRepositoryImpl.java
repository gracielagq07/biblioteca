package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Author;

public class AuthorRepositoryImpl implements AuthorRepository {
    @Override
    public void addAuthor(Author author){

        String sql="INSERT INTO authors (name) VALUES (?)";

        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);){
            st.setString(1, author.getName());
            st.executeUpdate();
            } catch (SQLException e) {
           throw new RuntimeException("Error al añadir el autor" + e.getMessage());
        }
    }
}


            
    


