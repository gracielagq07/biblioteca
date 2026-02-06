package com.biblioteca.repository;

import java.util.List;

import com.biblioteca.model.Publisher;

public interface PublisherRepository {
    void addPublisher(Publisher publisher);

    List<Publisher> findAll();

    void updatePublisher(Publisher publisher);

    void deleteById(int id);

    List<Publisher> findByName(String name);
}
