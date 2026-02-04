package com.biblioteca.controller;

import com.biblioteca.model.Genre;
import com.biblioteca.repository.GenreRepository;

public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository){
        this.genreRepository=genreRepository;
    }

    public void addGenre(Genre genre){
        genreRepository.addGenre(genre);
    }
}
