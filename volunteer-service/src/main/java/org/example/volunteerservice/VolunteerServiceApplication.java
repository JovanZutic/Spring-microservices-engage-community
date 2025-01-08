package org.example.volunteerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VolunteerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerServiceApplication.class, args);
    }

}
