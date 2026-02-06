package com.biblioteca.view;

import java.util.List;
import java.util.Scanner;
import com.biblioteca.model.Genre;

public class GenreMenuView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        int option = -1;
        do {
            System.out.println("\n----- GESTIÓN DE GÉNEROS -----");
            System.out.println("1. Listar géneros");
            System.out.println("2. Añadir género");
            System.out.println("3. Editar género");
            System.out.println("4. Eliminar género");
            System.out.println("5. Buscar género por nombre");
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

    public void showGenres(List<Genre> genres) {
        if (genres.isEmpty()) {
            System.out.println("No hay géneros.");
            return;
        }
        genres.forEach(g -> System.out.println("ID: " + g.getId() + " | Género: " + g.getGenre()));
    }

    public String askForName() {
        System.out.print("Nombre del género: ");
        return scanner.nextLine();
    }

    public int askForGenreId() {
        int id = -1;
        do {
            System.out.print("ID del género: ");
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

    public Genre askForNewGenre() {
        Genre g = new Genre();
        System.out.print("Nombre del género: ");
        g.setGenre(scanner.nextLine());
        return g;
    }

    public Genre askForGenreToEdit() {
        Genre g = new Genre();
        g.setId(askForGenreId());
        System.out.print("Nuevo nombre: ");
        g.setGenre(scanner.nextLine());
        return g;
    }
}
