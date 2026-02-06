package com.biblioteca.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private String isbn;
    private String description;
    private Publisher publisher;
    private List<Genre> genres;
    private List<Author> authors;

    public Book(int id, String title, String isbn, String description, Publisher publisher, List<Genre> genres,
            List<Author> authors) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publisher = publisher;
        this.genres = genres;
        this.authors = authors;
    }

    public Book() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}