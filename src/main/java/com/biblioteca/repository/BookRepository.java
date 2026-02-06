package com.biblioteca.repository;

import java.sql.Connection;
import java.util.List;

import com.biblioteca.model.Book;

public interface BookRepository {
    void addBook(Book book);

    List<Book> findAll();

    void updateBook(Book book);

    void deleteById(int id);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

    boolean bookExistsByIsbn(Connection con, String isbn);

    int getOrCreatePublisher(Connection con, String name);

    int getOrCreateAuthor(Connection con, String name);

    void linkBookAuthor(Connection con, int bookId, int authorId);

    int getOrCreateGenre(Connection con, String name);

    void linkBookGenre(Connection con, int bookId, int genreId);

    Book findById(int id);

}
