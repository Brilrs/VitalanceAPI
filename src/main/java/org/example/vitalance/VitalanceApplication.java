package org.example.vitalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VitalanceApplication {

    public static void main(String[] args) {

        System.out.println("HF_API_KEY=" + System.getenv("HF_API_KEY"));
        SpringApplication.run(VitalanceApplication.class, args);
    }

}
