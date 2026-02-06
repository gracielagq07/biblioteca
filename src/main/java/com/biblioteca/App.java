package com.biblioteca;

import com.biblioteca.controller.*;
import com.biblioteca.repository.*;
import com.biblioteca.view.*;

public class App {
    public static void main(String[] args) {

        BookRepositoryImpl bookRepo = new BookRepositoryImpl();
        AuthorRepositoryImpl authorRepo = new AuthorRepositoryImpl();
        PublisherRepositoryImpl publisherRepo = new PublisherRepositoryImpl();
        GenreRepositoryImpl genreRepo = new GenreRepositoryImpl();

        BookMenuView bookMenuView = new BookMenuView();
        BookView bookView = new BookView();
        AuthorMenuView authorView = new AuthorMenuView();
        PublisherMenuView publisherView = new PublisherMenuView();
        GenreMenuView genreView = new GenreMenuView();

        BookController bookController = new BookController(bookRepo, bookMenuView, bookView);
        AuthorController authorController = new AuthorController(authorRepo, authorView);
        PublisherController publisherController = new PublisherController(publisherRepo, publisherView);
        GenreController genreController = new GenreController(genreRepo, genreView);

        MainController mainController = new MainController(
                new MainMenuView(),
                bookController,
                authorController,
                publisherController,
                genreController);

        mainController.start();
    }
}