package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Publisher;

public class PublisherRepositoryImpl implements PublisherRepository{

    @Override
    public void addPublisher(Publisher publisher){
        String sql="INSERT INTO publishers (name) VALUES (?)";

        try (
            Connection connection = DBManager.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);
        ){
            st.setString(1, publisher.getName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir la editorial" + e.getMessage());
        }
    }

}
