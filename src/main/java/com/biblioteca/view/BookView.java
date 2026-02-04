package com.biblioteca.view;

import java.util.Scanner;

import com.biblioteca.controller.BookController;
import com.biblioteca.model.Book;

public class BookView {
    private final BookController bookController;

    public BookView(BookController bookController){
        this.bookController=bookController;

    }

    public void addBook(Scanner sc){
        System.out.println("AÑADE UN LIBRO");
        System.out.print("Introduce el nombre del libro: ");
        String title=sc.nextLine();
        System.out.print("Introduce el ISBN del libro: ");
        String isbn=sc.nextLine();
        System.out.print("Introduce una descripción para el libro: ");
        String description=sc.nextLine();

        Book book= new Book(title, isbn, description);
        bookController.addBook(book);
        System.out.println("Libro añadido con éxito");
    }
}
