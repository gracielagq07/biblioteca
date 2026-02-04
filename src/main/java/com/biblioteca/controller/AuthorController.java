package com.biblioteca.controller;

import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;

public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }

    public void addAuthor(Author author){
        authorRepository.addAuthor(author);
    }
}
