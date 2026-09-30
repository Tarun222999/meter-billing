package com.cloudbox.metering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan 
@SpringBootApplication
public class MeteringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteringApplication.class, args);
	}

}
