package com.biblioteca.view;
import java.util.Scanner;
import com.biblioteca.controller.AuthorController;
import com.biblioteca.model.Author;

public class AuthorView {
    private final AuthorController authorController;

    public AuthorView(AuthorController authorController){
        this.authorController=authorController;
    }

    public void addAuthor(Scanner sc){
        System.out.println("AÑADE UN AUTOR ");
        System.out.print("Introduce el nombre de un autor: ");
        String name=sc.nextLine();
        Author author=new Author(name);
        authorController.addAuthor(author);
        System.out.println("Autor añadido con éxito");
    }
}
