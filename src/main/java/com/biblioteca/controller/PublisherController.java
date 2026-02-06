package com.biblioteca.controller;

import com.biblioteca.model.Publisher;
import com.biblioteca.repository.PublisherRepository;
import com.biblioteca.view.PublisherMenuView;

public class PublisherController {
    private final PublisherRepository publisherRepository;
    private final PublisherMenuView view;

    public PublisherController(PublisherRepository publisherRepository, PublisherMenuView view) {
        this.publisherRepository = publisherRepository;
        this.view = view;
    }

    public void addPublisher() {
        Publisher publisher = view.askForNewPublisher();
        publisherRepository.addPublisher(publisher);
        System.out.println("Editorial añadida correctamente.");
    }

    public void editPublisher() {
        Publisher p = view.askForPublisherToEdit();
        publisherRepository.updatePublisher(p);
        System.out.println("Editorial actualizada correctamente.");
    }

    public void deletePublisher() {
        int id = view.askForPublisherId();
        publisherRepository.deleteById(id);
        System.out.println("Editorial eliminada correctamente.");
    }

    public void start() {
        int option;
        do {
            option = view.showMenu();
            switch (option) {
                case 1 -> view.showPublishers(publisherRepository.findAll());
                case 2 -> addPublisher();
                case 3 -> editPublisher();
                case 4 -> deletePublisher();
                case 5 -> view.showPublishers(publisherRepository.findByName(view.askForName()));
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }
        } while (option != 0);
    }
}
