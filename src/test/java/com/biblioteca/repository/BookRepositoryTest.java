package com.biblioteca.repository;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.biblioteca.model.Book;

public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Libro 1");

        Book book2 = new Book();
        book2.setTitle("Libro 2");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> books = bookRepository.findAll();

        assertEquals(2, books.size());
        assertEquals("Libro 1", books.get(0).getTitle());
        assertEquals("Libro 2", books.get(1).getTitle());

        verify(bookRepository).findAll();
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("Nuevo Libro");

        doNothing().when(bookRepository).addBook(book);

        bookRepository.addBook(book);

        verify(bookRepository).addBook(book);
    }

    @Test
    public void testFindByTitle() {
        Book book = new Book();
        book.setTitle("Clean Code");

        when(bookRepository.findByTitle("Clean")).thenReturn(List.of(book));

        List<Book> result = bookRepository.findByTitle("Clean");

        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());

        verify(bookRepository).findByTitle("Clean");
    }
}
