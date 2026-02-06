package com.biblioteca.repository;

import java.util.List;

import com.biblioteca.model.Author;

public interface AuthorRepository {
    void addAuthor(Author author);

    List<Author> findAll();

    void updateAuthor(Author author);

    void deleteById(int id);

    List<Author> findByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);

}
