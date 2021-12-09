package br.hspm.isolamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@SpringBootApplication
@EnableAsync
public class IsolamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsolamentoApplication.class, args);
	}

}
