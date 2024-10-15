package org.tomodachi.controllers.event;

import java.util.UUID;

class EventNotFoundException extends RuntimeException {
    EventNotFoundException(UUID id) {
        super("Could not find event " + id);
    }
}
