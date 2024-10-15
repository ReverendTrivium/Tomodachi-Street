package org.tomodachi.service;

import org.tomodachi.model.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {

    List< Event > findAll();

    void save(Event event);

    Event findById(UUID id);

    void deleteById(UUID id);

}

