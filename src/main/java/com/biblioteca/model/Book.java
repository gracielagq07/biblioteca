package com.biblioteca.model;

public class Book {
private int id;
private String title;
private String isbn;
private String description;

public Book(String title, String isbn, String description){
    this.title=title;
    this.isbn=isbn;
    this.description=description;
}

public Book(){}


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
}