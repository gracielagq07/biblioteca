package com.biblioteca.repository;

import java.util.List;

import com.biblioteca.model.Genre;

public interface GenreRepository {
    void addGenre(Genre genre);

    List<Genre> findAll();

    void updateGenre(Genre genre);

    void deleteById(int id);

    List<Genre> findByName(String genre);
}
