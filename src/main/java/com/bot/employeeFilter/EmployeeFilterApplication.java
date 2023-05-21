package com.bot.employeeFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeFilterApplication.class, args);
	}

}
