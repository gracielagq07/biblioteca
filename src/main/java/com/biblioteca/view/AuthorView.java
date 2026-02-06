package com.biblioteca.view;

import java.util.Scanner;
import com.biblioteca.model.Author;

public class AuthorView {

    private final Scanner scanner = new Scanner(System.in);

    public Author askForNewAuthor() {
        Author author = new Author();
        String name;
        do {
            System.out.print("Introduce el nombre del autor: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intenta de nuevo.");
            }
        } while (name.isEmpty());
        author.setName(name);
        return author;
    }

    public Author askForAuthorToEdit() {
        Author author = new Author();
        System.out.print("ID del autor: ");
        author.setId(Integer.parseInt(scanner.nextLine()));
        System.out.print("Nuevo nombre: ");
        author.setName(scanner.nextLine());
        return author;
    }

    public int askForAuthorId() {
        System.out.print("ID del autor: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String askForName() {
        System.out.print("Nombre del autor a buscar: ");
        return scanner.nextLine();
    }
}