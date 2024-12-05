package com.SistemPos.sistemapos;

import com.SistemPos.sistemapos.repository.UserRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaposApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaposApplication.class, args);
	}
}
