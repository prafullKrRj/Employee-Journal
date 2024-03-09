package com.prafull.employeejournalback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.prafull.employeejournalback")
public class EmployeeJournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeJournalApplication.class, args);
    }

}
