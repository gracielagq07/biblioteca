package com.biblioteca.view;

import java.util.Scanner;
import com.biblioteca.model.Publisher;

public class PublisherView {

    private final Scanner scanner = new Scanner(System.in);

    public Publisher askForNewPublisher() {
        System.out.println("AÑADE UNA EDITORIAL");
        System.out.print("Introduce el nombre de una editorial: ");

        Publisher publisher = new Publisher();
        publisher.setName(scanner.nextLine());

        return publisher;
    }

    public Publisher askForPublisherToEdit() {
        Publisher publisher = new Publisher();
        System.out.print("ID de la editorial: ");
        publisher.setId(Integer.parseInt(scanner.nextLine()));
        System.out.print("Nuevo nombre: ");
        publisher.setName(scanner.nextLine());
        return publisher;
    }

    public int askForPublisherId() {
        System.out.print("ID de la editorial: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String askForName() {
        System.out.print("Nombre de la editorial a buscar: ");
        return scanner.nextLine();
    }

    public void showPublishers(java.util.List<Publisher> publishers) {
        if (publishers.isEmpty()) {
            System.out.println("No hay editoriales.");
            return;
        }
        publishers.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getName()));
    }
}
