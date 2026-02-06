package com.biblioteca.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Author;
import com.biblioteca.model.Book;
import com.biblioteca.model.Genre;
import com.biblioteca.model.Publisher;
import com.biblioteca.repository.BookRepositoryImpl;
import com.biblioteca.utils.colors;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public Book askForNewBook() {
        Book book = new Book();
        System.out.println(colors.MAGENTA + "AÑADE UN LIBRO" + colors.RESET);

        String title;
        do {
            System.out.print(colors.BG_CYAN + "Título:" + colors.RESET + " ");
            title = scanner.nextLine().trim();
            if (title.isBlank())
                System.out.println(colors.BG_YELLOW + "El título no puede estar vacío." + colors.RESET);
        } while (title.isBlank());
        book.setTitle(title);
String isbn;

do {
    System.out.print(colors.BG_CYAN + "ISBN: " + colors.RESET + " ");
    isbn = scanner.nextLine().trim();

    if (isbn.isBlank()) {
        System.out.println(colors.BG_YELLOW + "El ISBN no puede estar vacío." + colors.RESET);
        continue;
    }

    // Quitar guiones y espacios
    isbn = isbn.replaceAll("[- ]", "");

    // Validar longitud
    if (isbn.length() < 10 || isbn.length() > 13) {
        System.out.println(colors.BG_RED + "El ISBN debe tener entre 10 y 13 caracteres." + colors.RESET);
        isbn = "";
        continue;
    }

    // ⚡ Verificar si ya existe en BD
    try (Connection con = DBManager.getConnection()) {
        BookRepositoryImpl repo = new BookRepositoryImpl();
        if (repo.bookExistsByIsbn(con, isbn)) {
            System.out.println(colors.BG_YELLOW + "El ISBN ya está registrado. Ingresa otro." + colors.RESET);
            isbn = ""; // para que el bucle continue
        }
    } catch (SQLException e) {
        System.out.println(colors.BG_RED + "Error al validar ISBN en la base de datos: " + e.getMessage() + colors.RESET);
        isbn = ""; // para que el bucle continue
    }

} while (isbn.isBlank());

book.setIsbn(isbn);



        String description;
        do {
            System.out.print(colors.BG_CYAN + "Descripción (máx. 200 caracteres):" + colors.RESET + " ");
            description = scanner.nextLine().trim();
            if (description.isBlank()) {
                System.out.println(colors.BG_YELLOW + "La descripción no puede estar vacía." + colors.RESET);
            } else if (description.length() > 200) {
                System.out
                        .println(colors.BG_RED + "La descripción no puede superar los 200 caracteres." + colors.RESET);
                description = "";
            }
        } while (description.isBlank());
        book.setDescription(description);

        System.out.print(colors.BG_CYAN + "Editorial:" + colors.RESET + " ");
        Publisher publisher = new Publisher();
        publisher.setName(scanner.nextLine().trim());
        book.setPublisher(publisher);

        List<Author> authors = new ArrayList<>();
        System.out.print(colors.BG_CYAN + "Autor(es) (separados por coma):" + colors.RESET + " ");
        for (String name : scanner.nextLine().split(",")) {
            if (!name.trim().isBlank()) {
                Author author = new Author();
                author.setName(name.trim());
                authors.add(author);
            }
        }
        book.setAuthors(authors);

        List<Genre> genres = new ArrayList<>();
        System.out.print(colors.BG_CYAN + "Género(s) (separados por coma):" + colors.RESET + " ");
        for (String name : scanner.nextLine().split(",")) {
            String trimmed = name.trim();
            if (!trimmed.isBlank()) {
                String capitalized = trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
                Genre genre = new Genre();
                genre.setGenre(capitalized);
                genres.add(genre);
            }
        }
        book.setGenres(genres);

        return book;

    }

    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public int askForInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println(colors.BG_RED + "Número inválido. Intenta de nuevo." + colors.RESET);
            }
        }
    }

    public List<Author> askForAuthors() {
        List<Author> authors = new ArrayList<>();
        System.out.print(colors.BG_CYAN + "Autores (separados por coma):" + colors.RESET + " ");
        String line = scanner.nextLine();
        for (String name : line.split(",")) {
            name = name.trim();
            if (!name.isBlank()) {
                Author a = new Author();
                a.setName(capitalize(name));
                authors.add(a);
            }
        }
        return authors;
    }

    public List<Genre> askForGenres() {
        List<Genre> genres = new ArrayList<>();
        System.out.print(colors.BG_CYAN + "Géneros (separados por coma):" + colors.RESET + " ");
        String line = scanner.nextLine();
        for (String name : line.split(",")) {
            name = name.trim();
            if (!name.isBlank()) {
                Genre g = new Genre();
                g.setGenre(capitalize(name));
                genres.add(g);
            }
        }
        return genres;
    }

    private String capitalize(String s) {
        if (s == null || s.isBlank())
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public int askForBookId() {
        System.out.print(colors.BG_CYAN + "ID del libro:" + colors.RESET + " ");
        return readInt();
    }

    public String askForTitle() {
        String title;
        do {
            System.out.print(colors.BG_CYAN + "Título a buscar:" + colors.RESET + " ");
            title = scanner.nextLine().trim();
            if (title.isBlank()) {
                System.out
                        .println(colors.BG_YELLOW + "El título no puede estar vacío. Intenta de nuevo." + colors.RESET);
            }
        } while (title.isBlank());
        return title;
    }

    public String askForAuthor() {
        String author;
        do {
            System.out.print(colors.BG_CYAN + "Autor a buscar:" + colors.RESET + " ");
            author = scanner.nextLine().trim();
            if (author.isBlank()) {
                System.out
                        .println(colors.BG_YELLOW + "El autor no puede estar vacío. Intenta de nuevo." + colors.RESET);
            }
        } while (author.isBlank());
        return author;
    }

    public String askForGenre() {
        String genre;
        do {
            System.out.print(colors.BG_CYAN + "Género a buscar:" + colors.RESET + " ");
            genre = scanner.nextLine().trim();
            if (genre.isBlank()) {
                System.out
                        .println(colors.BG_YELLOW + "El género no puede estar vacío. Intenta de nuevo." + colors.RESET);
            }
        } while (genre.isBlank());
        return capitalize(genre);
    }

    public void showBooks(List<Book> books, boolean showDescription) {

        if (books.isEmpty()) {
            System.out.println(colors.BG_RED + "No se encontraron libros." + colors.RESET);
            return;
        }
        System.out.println(colors.BG_MAGENTA + "\n----- LISTADO DE LIBROS -----" + colors.RESET);
        for (Book book : books) {
            String authors = book.getAuthors()
                    .stream()
                    .map(Author::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            String genres = book.getGenres()
                    .stream()
                    .map(Genre::getGenre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            StringBuilder output = new StringBuilder(
                    colors.BLUE + "ID: " + colors.RESET + book.getId() +
                            colors.BLUE + " | Título: " + colors.RESET + book.getTitle() +
                            colors.BLUE + " | ISBN: " + colors.RESET + book.getIsbn() +
                            colors.BLUE + " | Autor(es): " + colors.RESET + authors +
                            colors.BLUE + " | Editorial: " + colors.RESET + book.getPublisher().getName() +
                            colors.BLUE + " | Género(s): " + colors.RESET + genres);
            if (showDescription) {
                output.append(colors.BLUE + " | Descripción: " + colors.RESET).append(book.getDescription() );
            }
            System.out.println(output);
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print(colors.BG_CYAN + "Introduce un número válido:" + colors.RESET + " ");
            }
        }
    }

    public Book askForBookToEdit(int id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }

}
