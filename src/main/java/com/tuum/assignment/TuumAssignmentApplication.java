package com.tuum.assignment;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableRabbit
public class TuumAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuumAssignmentApplication.class, args);
	}

}
