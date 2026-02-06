package com.biblioteca.controller;

import com.biblioteca.model.Genre;
import com.biblioteca.repository.GenreRepository;
import com.biblioteca.view.GenreMenuView;

public class GenreController {
    private final GenreRepository genreRepository;
    private final GenreMenuView view;

    public GenreController(GenreRepository genreRepository, GenreMenuView view) {
        this.genreRepository = genreRepository;
        this.view = view;
    }

    public void addGenre(Genre genre) {
        Genre lit = view.askForNewGenre();
        genreRepository.addGenre(lit);
        System.out.println("Género añadido con éxito");
    }

    public void start() {
        int option;
        do {
            option = view.showMenu();
            switch (option) {
                case 1 -> view.showGenres(genreRepository.findAll());
                case 2 -> genreRepository.addGenre(view.askForNewGenre());
                case 3 -> genreRepository.updateGenre(view.askForGenreToEdit());
                case 4 -> genreRepository.deleteById(view.askForGenreId());
                case 5 -> view.showGenres(genreRepository.findByName(view.askForName()));
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }
        } while (option != 0);
    }

}