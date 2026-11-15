package br.com.unifecaf.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal. A anotação @SpringBootApplication liga a configuração
 * automática do Spring Boot e faz a varredura dos pacotes deste projeto.
 */
@SpringBootApplication
public class EstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstoqueApplication.class, args);
    }
}
