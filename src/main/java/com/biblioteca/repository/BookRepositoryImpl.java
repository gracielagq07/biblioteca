package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Book;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public void addBook(Book book) {
    
        String sql="INSERT INTO books (title, isbn, description) VALUES (?,?,?)";
        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);){
            st.setString(1, book.getTitle());
            st.setString(2, book.getIsbn());
            st.setString(3, book.getDescription());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir libro" + e.getMessage());
        }
    }

}
