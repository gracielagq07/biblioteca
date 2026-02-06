package com.biblioteca.controller;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.view.BookMenuView;
import com.biblioteca.view.BookView;

public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMenuView menuView;

    @Mock
    private BookView view;

    @InjectMocks
    private BookController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldListAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Libro 1");
        Book book2 = new Book();
        book2.setTitle("Libro 2");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));
        when(menuView.showMenu()).thenReturn(1, 0);

        controller.start();

        verify(bookRepository).findAll();
        verify(view).showBooks(anyList(), eq(false));
    }

    @Test
    public void shouldAddBookSuccessfully() {
        Book book = new Book();
        book.setTitle("Nuevo libro");

        when(menuView.showMenu()).thenReturn(2, 0);
        when(view.askForNewBook()).thenReturn(book);

        controller.start();

        verify(bookRepository).addBook(book);
    }

    @Test
    public void shouldSearchBookByTitle() {
        Book book = new Book();
        book.setTitle("Clean Code");

        when(menuView.showMenu()).thenReturn(5, 0);
        when(view.askForTitle()).thenReturn("Clean");
        when(bookRepository.findByTitle("Clean")).thenReturn(List.of(book));

        controller.start();

        verify(bookRepository).findByTitle("Clean");
        verify(view).showBooks(anyList(), eq(true));
    }

    @Test
    public void shouldDeleteBook() {
        when(menuView.showMenu()).thenReturn(4, 0);
        when(view.askForBookId()).thenReturn(1);

        controller.start();

        verify(bookRepository).deleteById(1);
    }

    @Test
    public void shouldEditBookTitleAndSave() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Antiguo");

        when(menuView.showMenu()).thenReturn(3, 0);
        when(view.askForBookId()).thenReturn(1);
        when(bookRepository.findById(1)).thenReturn(book);

        when(view.askForInt(anyString())).thenReturn(1).thenReturn(0);
        when(view.askForString(anyString())).thenReturn("Nuevo título");

        controller.start();

        verify(bookRepository).findById(1);
        verify(bookRepository).updateBook(book);
    }
}
