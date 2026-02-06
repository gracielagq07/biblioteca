package com.biblioteca.repository;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.biblioteca.model.Author;
import com.biblioteca.model.Book;
import com.biblioteca.model.Genre;
import com.biblioteca.model.Publisher;
import com.biblioteca.config.DBManager;

public class BookRepositoryImplTest {

    private BookRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new BookRepositoryImpl();

        try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS publishers (id_publisher SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL);");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS books (id_book SERIAL PRIMARY KEY, title VARCHAR(255), isbn VARCHAR(50), description TEXT, id_publisher INT, CONSTRAINT fk_publisher FOREIGN KEY (id_publisher) REFERENCES publishers(id_publisher));");
            stmt.execute("CREATE TABLE IF NOT EXISTS authors (id_author SERIAL PRIMARY KEY, name VARCHAR(255));");
            stmt.execute("CREATE TABLE IF NOT EXISTS genres (id_genre SERIAL PRIMARY KEY, genre VARCHAR(255));");
            stmt.execute("CREATE TABLE IF NOT EXISTS books_authors (id_book INT, id_author INT);");
            stmt.execute("CREATE TABLE IF NOT EXISTS books_genres (id_book INT, id_genre INT);");
        }
    }

    @After
    public void tearDown() throws Exception {
        try (Connection conn = DBManager.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM books_authors;");
            stmt.execute("DELETE FROM books_genres;");
            stmt.execute("DELETE FROM books;");
            stmt.execute("DELETE FROM publishers;");
            stmt.execute("DELETE FROM authors;");
            stmt.execute("DELETE FROM genres;");
        }
    }

    @Test
    public void testAddAndFindBook() {
        Publisher publisher = new Publisher();
        publisher.setName("Editorial Test");

        Author author = new Author();
        author.setName("Autor Test");

        Genre genre = new Genre();
        genre.setGenre("Genero Test");

        Book book = new Book();
        book.setTitle("Libro Test");
        book.setIsbn("1111");
        book.setDescription("Desc Original");
        book.setPublisher(publisher);

        book.setAuthors(List.of(author));
        book.setGenres(List.of(genre));

        repository.addBook(book);

        List<Book> books = repository.findAll();
        assertEquals(1, books.size());
        assertEquals("Libro Test", books.get(0).getTitle());

        List<Book> booksByTitle = repository.findByTitle("Libro Test");
        assertEquals(1, booksByTitle.size());

        List<Book> booksByAuthor = repository.findByAuthor("Autor Test");
        assertEquals(1, booksByAuthor.size());

        List<Book> booksByGenre = repository.findByGenre("Genero Test");
        assertEquals(1, booksByGenre.size());

        Book bookById = repository.findById(books.get(0).getId());
        assertNotNull(bookById);
        assertEquals("Libro Test", bookById.getTitle());
    }

    @Test
    public void testUpdateBook() {
        Publisher publisher = new Publisher();
        publisher.setName("Editorial Test");
        Book book = new Book();
        book.setTitle("Libro Original");
        book.setIsbn("1111");
        book.setDescription("Desc Original");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setName("Autor Test");
        Genre genre = new Genre();
        genre.setGenre("Genero Test");
        book.setAuthors(List.of(author));
        book.setGenres(List.of(genre));

        repository.addBook(book);

        Book stored = repository.findAll().get(0);
        stored.setTitle("Libro Modificado");
        repository.updateBook(stored);

        Book updated = repository.findById(stored.getId());
        assertEquals("Libro Modificado", updated.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Publisher publisher = new Publisher();
        publisher.setName("Editorial Test");
        Book book = new Book();
        book.setTitle("Libro Para Eliminar");
        book.setIsbn("9999");
        book.setDescription("Desc");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setName("Autor Test");
        Genre genre = new Genre();
        genre.setGenre("Genero Test");
        book.setAuthors(List.of(author));
        book.setGenres(List.of(genre));

        repository.addBook(book);

        Book stored = repository.findAll().get(0);
        repository.deleteById(stored.getId());

        List<Book> books = repository.findAll();
        assertEquals(0, books.size());
    }
}
