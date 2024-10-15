package org.tomodachi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

import org.tomodachi.service.EventService;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private EventService eventService;

    @Override
    public void run(String...args) throws Exception {
        System.out.println("JVM Time Zone: " + ZoneId.systemDefault());
    }
}
