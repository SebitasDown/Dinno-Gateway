package com.gateway.dinno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DinnoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinnoApplication.class, args);
	}

}
