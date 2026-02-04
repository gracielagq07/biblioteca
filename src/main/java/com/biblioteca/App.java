package com.biblioteca;
import java.util.Scanner;

import com.biblioteca.controller.AuthorController;
import com.biblioteca.controller.BookController;
import com.biblioteca.controller.GenreController;
import com.biblioteca.controller.PublisherController;
import com.biblioteca.repository.AuthorRepositoryImpl;
import com.biblioteca.repository.BookRepositoryImpl;
import com.biblioteca.repository.GenreRepositoryImpl;
import com.biblioteca.repository.PublisherRepositoryImpl;
import com.biblioteca.view.AuthorView;
import com.biblioteca.view.BookView;
import com.biblioteca.view.GenreView;
import com.biblioteca.view.PublisherView;

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
            
            PublisherRepositoryImpl pubRepo=new PublisherRepositoryImpl();
            PublisherController publisherController=new PublisherController(pubRepo);
            PublisherView publisherView=new PublisherView(publisherController);

            GenreRepositoryImpl GenRepo=new GenreRepositoryImpl();
            GenreController genreController=new GenreController(GenRepo);
            GenreView genreView=new GenreView(genreController);

            bookView.addBook(scanner);
            authorView.addAuthor(scanner);
            publisherView.addPublisher(scanner);
            genreView.addGenre(scanner);
        } catch (Exception e) {
           System.err.println(e.getMessage());
        }finally{
            scanner.close();
        }
       
    }
}
