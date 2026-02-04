package com.biblioteca;

import java.sql.SQLException;
import java.util.Scanner;

import com.biblioteca.config.DBManager;
import com.biblioteca.controller.BookController;
import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepositoryImpl;
import com.biblioteca.view.BookView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner=new Scanner(System.in);
        try {
            BookRepositoryImpl repo= new BookRepositoryImpl();
            BookController bookController= new BookController(repo);
            BookView bookView= new BookView(bookController);
            bookView.addBook(scanner);
        } catch (Exception e) {
           System.err.println(e.getMessage());
        }finally{
            scanner.close();
        }
       
    }
}
