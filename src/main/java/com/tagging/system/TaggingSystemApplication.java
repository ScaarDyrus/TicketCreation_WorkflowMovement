package com.tagging.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaggingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaggingSystemApplication.class, args);
        System.out.println("TicketCreation_WorkflowMovement application started successfully!");
    }
}
