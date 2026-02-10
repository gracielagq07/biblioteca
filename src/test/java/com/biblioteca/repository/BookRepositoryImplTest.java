package com.biblioteca.repository;

import com.biblioteca.model.Author;
import com.biblioteca.model.Book;
import com.biblioteca.model.Publisher;
import com.biblioteca.model.Genre;
import com.biblioteca.config.DBManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;


public class BookRepositoryImplTest {

    private static BookRepository repo;
    private Connection testCon;

    @BeforeEach
    public static void setupClass() throws Exception {
        repo = new BookRepositoryImpl();
    }

    @BeforeEach
    public void setupTest() throws Exception {
        testCon = DBManager.getConnection();
        testCon.setAutoCommit(false);
    }

    @AfterAll
    public void cleanupTest() throws Exception {
        testCon.rollback();
        testCon.close();
    }

    @Test
    public void testAddAndFindBook() throws Exception {
        String isbn = "ISBN" + System.currentTimeMillis();
        String description ="Descripción Test" + System.currentTimeMillis();

        Book book = new Book();
        book.setTitle("Libro Test " + System.currentTimeMillis());
        book.setIsbn(isbn);
        book.setDescription(description);

        Publisher publisher = new Publisher();
        publisher.setName("Editorial Test");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setName("Autor Test");
        book.setAuthors(List.of(author));

        Genre genre = new Genre();
        genre.setGenre("Genero Test");
        book.setGenres(List.of(genre));

        


        repo.addBook(book);

        List<Book> findThem = repo.findByTitle(book.getTitle());
        assertFalse(findThem.isEmpty(), "El libro debería encontrarse" );

        Book find = findThem.get(0);
        assertEquals(isbn, find.getIsbn());
        assertEquals(description, find.getDescription());
        assertEquals("Editorial Test", find.getPublisher().getName());
        assertEquals("Autor Test", find.getAuthors().get(0).getName());
        assertEquals("Genero Test", find.getGenres().get(0).getGenre());
    }

    @Test
    public void testBookExistsByIsbn() throws Exception {
        String isbn = "ISBN" + System.currentTimeMillis();

        Book book = new Book();
        book.setTitle("Libro ISBN Test");
        book.setIsbn(isbn);
        book.setDescription("Descripción Temp");

        Publisher publisher = new Publisher();
        publisher.setName("Editorial Temp");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setName("Autor Temp");
        book.setAuthors(List.of(author));

        Genre genre = new Genre();
        genre.setGenre("Genero Temp");
        book.setGenres(List.of(genre));

        repo.addBook(book);
        assertTrue(repo.bookExistsByIsbn(testCon, isbn), "El ISBN debería existir en la base de datos");
    }

    @Test
    public void testDeleteBook() throws Exception {
        String isbn = "ISBN" + System.currentTimeMillis();

        Book book = new Book();
        book.setTitle("Libro Delete Test");
        book.setIsbn(isbn);
        book.setDescription("Descripción Delete");

        Publisher publisher = new Publisher();
        publisher.setName("Editorial Delete");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setName("Autor Delete");
        book.setAuthors(List.of(author));

        Genre genre = new Genre();
        genre.setGenre("Genero Delete");
        book.setGenres(List.of(genre));

        repo.addBook(book);

        List<Book> findThem = repo.findByTitle(book.getTitle());
        assertFalse(findThem.isEmpty());
        Book inserted = findThem.get(0);

        repo.deleteById(inserted.getId());

        Book finded = repo.findById(inserted.getId());
        assertNull(finded, "El libro debería haber sido eliminado");
    }
}
