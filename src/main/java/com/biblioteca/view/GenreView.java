package com.biblioteca.view;

import java.util.Scanner;
import com.biblioteca.controller.GenreController;
import com.biblioteca.model.Genre;

public class GenreView {
    private final GenreController genreController;

    public GenreView(GenreController genreController) {
        this.genreController = genreController;
    }

    public void addGenre(Scanner sc) {
        System.out.println("AÑADE UN GÉNERO LITERARIO: ");
        System.out.print("Introduce un género literario: ");
        String genre = sc.nextLine();
        Genre lit = new Genre();
        lit.setGenre(genre);
        genreController.addGenre(lit);
        System.out.println("Género literario añadido con éxito");
    }
}
