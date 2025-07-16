package com.cqrs;

import com.cqrs.command.api.exception.ProductServiceErrorHandler;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CqrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CqrsApplication.class, args);
	}

}
