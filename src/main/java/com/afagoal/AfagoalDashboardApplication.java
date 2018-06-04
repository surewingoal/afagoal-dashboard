package com.afagoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AfagoalDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfagoalDashboardApplication.class, args);
	}
}
