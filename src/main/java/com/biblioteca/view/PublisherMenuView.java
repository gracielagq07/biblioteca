package com.biblioteca.view;

import java.util.List;
import java.util.Scanner;
import com.biblioteca.model.Publisher;

public class PublisherMenuView {

    private final Scanner scanner = new Scanner(System.in);

    public int showMenu() {
        System.out.println("\n----- GESTIÓN DE EDITORIALES -----");
        System.out.println("1. Listar editoriales");
        System.out.println("2. Añadir editorial");
        System.out.println("3. Editar editorial");
        System.out.println("4. Eliminar editorial");
        System.out.println("5. Buscar editorial por nombre");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void showPublishers(List<Publisher> publishers) {
        if (publishers.isEmpty()) {
            System.out.println("No hay editoriales.");
            return;
        }
        publishers.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getName()));
    }

    public Publisher askForNewPublisher() {
        Publisher p = new Publisher();
        System.out.print("Nombre de la editorial: ");
        p.setName(scanner.nextLine());
        return p;
    }

    public Publisher askForPublisherToEdit() {
        Publisher p = new Publisher();
        System.out.print("ID de la editorial: ");
        p.setId(Integer.parseInt(scanner.nextLine()));
        System.out.print("Nuevo nombre: ");
        p.setName(scanner.nextLine());
        return p;
    }

    public int askForPublisherId() {
        System.out.print("ID de la editorial: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String askForName() {
        System.out.print("Nombre de la editorial a buscar: ");
        return scanner.nextLine();
    }
}
