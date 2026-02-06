package com.biblioteca.controller;

import com.biblioteca.utils.colors;
import com.biblioteca.view.MainMenuView;

public class MainController {
    private final MainMenuView view;
    private final BookController bookController;
    private final AuthorController authorController;
    private final PublisherController publisherController;
    private final GenreController genreController;

    public MainController(
            MainMenuView view,
            BookController bookController,
            AuthorController authorController,
            PublisherController publisherController,
            GenreController genreController) {
        this.view = view;
        this.bookController = bookController;
        this.authorController = authorController;
        this.publisherController = publisherController;
        this.genreController = genreController;
    }

    public void start() {
        int option;
        do {
            option = view.showMenu();
            switch (option) {
                case 1 -> bookController.start();
                case 2 -> authorController.start();
                case 3 -> publisherController.start();
                case 4 -> genreController.start();
                case 0 -> System.out.println(colors.BG_BLUE + "Hasta pronto!!!" + colors.RESET);
                default -> System.out.println(colors.BG_RED + "Opción no válida" + colors.RESET);
            }
        } while (option != 0);
    }

}
