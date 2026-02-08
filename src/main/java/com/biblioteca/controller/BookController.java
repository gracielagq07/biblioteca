package com.biblioteca.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Author;
import com.biblioteca.model.Book;
import com.biblioteca.model.Genre;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.utils.colors;
import com.biblioteca.view.BookMenuView;
import com.biblioteca.view.BookView;

public class BookController {

    private final BookRepository bookRepository;
    private final BookMenuView menuView;
    private final BookView view;

    public BookController(BookRepository bookRepository, BookMenuView menuView, BookView view) {
        this.bookRepository = bookRepository;
        this.menuView = menuView;
        this.view = view;
    }

    private void addBook() {
        Book book = view.askForNewBook();
        try {
            bookRepository.addBook(book);
            System.out.println(colors.GREEN + "Libro añadido correctamente." + colors.RESET);
        } catch (RuntimeException e) {
            System.out.println(colors.RED + "Error al añadir libro: " + e.getMessage() + colors.RESET);
        }
    }

    private void listAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println(colors.RED + "No se encontraron libros en la base de datos." + colors.RESET);
        } else {
            view.showBooks(books, false);
        }
    }

    private void searchByTitle() {
        String title = view.askForTitle();
        List<Book> books = bookRepository.findByTitle(title);
        view.showBooks(books, true);
    }

    private void searchByAuthor() {
        String author = view.askForAuthor();
        List<Book> books = bookRepository.findByAuthor(author);
        view.showBooks(books, true);
    }

    private void searchByGenre() {
        String genre = view.askForGenre();
        List<Book> books = bookRepository.findByGenre(genre);
        view.showBooks(books, false);
    }

    private void editBook() {
        int id = view.askForBookId();
        Book book = bookRepository.findById(id);
        if (book == null) {
            System.out.println(colors.BG_YELLOW + "No se encontró el libro con ID " + colors.RESET + id + " ");
            return;
        }
        boolean editing = true;
        while (editing) {
            System.out.println(colors.BG_MAGENTA + "\n--- Editar Libro ---" + colors.RESET);
            System.out.println(colors.GREEN + "1." + colors.RESET + colors.CYAN + " Título" + colors.RESET);
            System.out.println(colors.GREEN + "2." + colors.RESET + colors.CYAN + " ISBN " + colors.RESET);
            System.out.println(colors.GREEN + "3." + colors.RESET + colors.CYAN + " Descripción" + colors.RESET);
            System.out.println(colors.GREEN + "4." + colors.RESET + colors.CYAN + " Autores" + colors.RESET);
            System.out.println(colors.GREEN + "5." + colors.RESET + colors.CYAN + " Géneros" + colors.RESET);
            System.out.println(colors.GREEN + "0." + colors.RESET + colors.CYAN + " Guardar y salir" + colors.RESET);
            int choice = view.askForInt(colors.BG_GREEN + "Seleccione el campo a editar:" + colors.RESET + " ");
            switch (choice) {
                case 1 -> {
                    String newTitle = view.askForString(colors.CYAN + "Nuevo título:" + colors.RESET + " ");
                    if (!newTitle.isBlank())
                        book.setTitle(newTitle);
                    else
                        System.out.println(colors.BG_RED + "El título no puede estar vacío." + colors.RESET);
                }
                case 2 -> {
                    String newIsbn = view.askForString(colors.CYAN + "Nuevo ISBN:" + colors.RESET + " ");
                    if (!newIsbn.isBlank())
                        book.setIsbn(newIsbn);
                    else
                        System.out.println(colors.BG_RED + "El ISBN no puede estar vacío." + colors.RESET);
                }
                case 3 -> {
                    String newDesc;
                    do {
                        newDesc = view
                                .askForString(colors.CYAN + "Nueva descripción (máx. 200 caracteres):" + colors.RESET + " ");
                        if (newDesc.isBlank()) {
                            System.out.println(colors.BG_RED + "La descripción no puede estar vacía." + colors.RESET);
                        } else if (newDesc.length() > 200) {
                            System.out.println(colors.BG_RED + "La descripción no puede superar los 200 caracteres."
                                    + colors.RESET);
                            newDesc = "";
                        }
                    } while (newDesc.isBlank());
                    book.setDescription(newDesc);
                }
                case 4 -> {
                    List<Author> authors = view.askForAuthors();
                    if (!authors.isEmpty())
                        book.setAuthors(authors);
                    else
                        System.out.println(colors.BG_YELLOW + "Debe agregar al menos un autor." + colors.RESET);
                }
                case 5 -> {
                    List<Genre> genresInput = view.askForGenres();
                    if (!genresInput.isEmpty()) {
                        List<Genre> genresWithId = new ArrayList<>();
                        try (Connection con = DBManager.getConnection()) {
                            for (Genre g : genresInput) {
                                int genreId = bookRepository.getOrCreateGenre(con, g.getGenre());
                                Genre genreWithId = new Genre();
                                genreWithId.setId(genreId);
                                genreWithId.setGenre(g.getGenre());
                                genresWithId.add(genreWithId);
                            }
                        } catch (Exception e) {
                            System.out.println(
                                    colors.BG_RED + "Error al procesar géneros: " + e.getMessage() + colors.RESET);
                        }
                        book.setGenres(genresWithId);
                    } else {
                        System.out.println(colors.YELLOW + "Debe agregar al menos un género." + colors.RESET);
                    }
                }
                case 0 -> editing = false;
                default -> System.out.println(colors.RED + "Opción no válida." + colors.RESET);
            }
        }

        try {
            bookRepository.updateBook(book);
            System.out.println(colors.BG_GREEN + "Libro actualizado correctamente." + colors.RESET);
        } catch (RuntimeException e) {
            System.out.println(colors.BG_RED + "Error al actualizar libro: " + e.getMessage() + colors.RESET);
        }
    }

    private void deleteBook() {
        int id = view.askForBookId();
        try {
            bookRepository.deleteById(id);
        } catch (RuntimeException e) {
            System.out.println(colors.RED + "Error: " + e.getMessage() + colors.RESET);
        }
    }

    public void start() {
        int option;
        do {
            option = menuView.showMenu();
            switch (option) {
                case 1 -> listAllBooks();
                case 2 -> addBook();
                case 3 -> editBook();
                case 4 -> deleteBook();
                case 5 -> searchByTitle();
                case 6 -> searchByAuthor();
                case 7 -> searchByGenre();
                case 0 -> System.out.println(colors.BG_YELLOW + "Saliendo al menú principal..." + colors.RESET);
                default -> System.out.println(colors.BG_RED + "Opción no válida" + colors.RESET);
            }
        } while (option != 0);
    }
}
