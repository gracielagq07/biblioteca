package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;

public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository=bookRepository;

    }

    public void addBook(Book book){
        bookRepository.addBook(book);
    }


}
