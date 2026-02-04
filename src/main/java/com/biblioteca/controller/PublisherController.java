package com.biblioteca.controller;

import com.biblioteca.model.Publisher;
import com.biblioteca.repository.PublisherRepository;

public class PublisherController {
    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository){
        this.publisherRepository=publisherRepository;
    }

    public void addPublisher(Publisher publisher){
        publisherRepository.addPublisher(publisher);
    }
}
