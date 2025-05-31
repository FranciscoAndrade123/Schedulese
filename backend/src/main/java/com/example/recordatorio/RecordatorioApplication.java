package com.example.recordatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecordatorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordatorioApplication.class, args);
	}

}
