package br.unipar.programacaoweb.estacaocemtempobrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EstacaoCemTempoBrowApplication {

    public static void main(String[] args) {

        SpringApplication.run(EstacaoCemTempoBrowApplication.class, args);
        System.out.println("""
            ******************************************
            * EcoMonitor API está rodando!           *
            *                                        *
            * Acesse: http://localhost:8080         *
            * Swagger UI: /swagger-ui.html           *
            ******************************************
            """);
    }

}
