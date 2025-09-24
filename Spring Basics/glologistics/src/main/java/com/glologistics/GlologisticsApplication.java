package com.glologistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GlologisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlologisticsApplication.class, args);
	}
}
