package com.biblioteca.controller;

import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;
import com.biblioteca.view.AuthorMenuView;

public class AuthorController {
    private final AuthorRepository authorRepository;
    private final AuthorMenuView view;

    public AuthorController(AuthorRepository authorRepository, AuthorMenuView view) {
        this.authorRepository = authorRepository;
        this.view = view;
    }

    public void addAuthor() {
        Author author;
        do {
            author = view.askForNewAuthor();
            if (authorRepository.existsByName(author.getName())) {
                System.out.println("Este autor ya está registrado. Intenta con otro nombre.");
                author = null;
            }
        } while (author == null);
        authorRepository.addAuthor(author);
        System.out.println("Autor añadido con éxito. ID: " + author.getId());
    }

    public void start() {
        int option;
        do {
            option = view.showMenu();
            switch (option) {
                case 1 -> view.showAuthors(authorRepository.findAll());
                case 2 -> addAuthor();
                case 3 -> editAuthor();
                case 4 -> authorRepository.deleteById(view.askForAuthorId());
                case 5 -> view.showAuthors(authorRepository.findByName(view.askForName()));
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }
        } while (option != 0);
    }

    public void editAuthor() {
        int id;
        do {
            id = view.askForAuthorId();
            if (!authorRepository.existsById(id)) {
                System.out.println("El ID introducido no existe. Intenta de nuevo.");
                id = -1;
            }
        } while (id == -1);
        String newName;
        do {
            System.out.print("Nuevo nombre: ");
            newName = view.getScanner().nextLine().trim();

            if (newName.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intenta de nuevo.");
            } else if (authorRepository.existsByName(newName)) {
                System.out.println("Este nombre ya está registrado. Elige otro.");
                newName = "";
            }
        } while (newName.isEmpty());

        Author author = new Author();
        author.setId(id);
        author.setName(newName);

        authorRepository.updateAuthor(author);
        System.out.println("Autor actualizado con éxito.");
    }
}
