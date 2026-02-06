package com.biblioteca.view;

import java.util.List;
import java.util.Scanner;

import com.biblioteca.model.Author;

public class AuthorMenuView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        int option = -1;
        do {
            System.out.println("\n----- GESTIÓN DE AUTORES -----");
            System.out.println("1. Listar autores");
            System.out.println("2. Añadir autor");
            System.out.println("3. Editar autor");
            System.out.println("4. Eliminar autor");
            System.out.println("5. Buscar autor por nombre");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
                if (option < 0 || option > 5) {
                    System.out.println("Opción incorrecta, introduce de nuevo.");
                    option = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción incorrecta, introduce de nuevo.");
            }
        } while (option == -1);

        return option;
    }

    public void showAuthors(List<Author> authors) {
        if (authors.isEmpty()) {
            System.out.println("No hay autores.");
            return;
        }

        authors.forEach(a -> System.out.println("ID: " + a.getId() + " - Autor(es): " + a.getName()));
    }

    public String askForName() {
        System.out.print("Nombre del autor: ");
        return scanner.nextLine();
    }

    public int askForAuthorId() {
        int id = -1;
        do {
            System.out.print("ID del autor: ");
            String input = scanner.nextLine();
            try {
                id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println("ID incorrecto, introduce un número positivo.");
                    id = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID incorrecto, introduce un número válido.");
            }
        } while (id == -1);
        return id;
    }

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
        Author a = new Author();
        a.setId(askForAuthorId());

        String newName;
        do {
            System.out.print("Nuevo nombre: ");
            newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intenta de nuevo.");
            }
        } while (newName.isEmpty());

        a.setName(newName);
        return a;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
