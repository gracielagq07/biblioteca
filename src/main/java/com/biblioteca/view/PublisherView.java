package com.biblioteca.view;

import java.util.Scanner;

import com.biblioteca.controller.PublisherController;
import com.biblioteca.model.Publisher;

public class PublisherView {
    private final PublisherController publisherController;

    public PublisherView(PublisherController publisherController){
        this.publisherController=publisherController;
    }

    public void addPublisher(Scanner sc){
        System.out.println("AÑADE UNA EDITORIAL");
        System.out.print("Introduce el nombre de una editorial: ");
        String name=sc.nextLine();
        Publisher publisher=new Publisher(name);
        publisherController.addPublisher(publisher);
        System.out.println("Editorial añadida con éxito");
    }
}
