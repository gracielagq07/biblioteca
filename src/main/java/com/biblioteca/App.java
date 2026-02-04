package com.biblioteca;
import java.util.Scanner;

import com.biblioteca.controller.AuthorController;
import com.biblioteca.controller.BookController;
import com.biblioteca.repository.AuthorRepositoryImpl;
import com.biblioteca.repository.BookRepositoryImpl;
import com.biblioteca.view.AuthorView;
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
            AuthorRepositoryImpl authorRepo=new AuthorRepositoryImpl();
            AuthorController authorController=new AuthorController(authorRepo);
            AuthorView authorView=new AuthorView(authorController);
            bookView.addBook(scanner);
            authorView.addAuthor(scanner);
        } catch (Exception e) {
           System.err.println(e.getMessage());
        }finally{
            scanner.close();
        }
       
    }
}
