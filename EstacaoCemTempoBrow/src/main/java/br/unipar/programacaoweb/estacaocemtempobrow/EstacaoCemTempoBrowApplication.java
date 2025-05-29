package br.unipar.programacaoweb.estacaocemtempobrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EstacaoCemTempoBrowApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(EstacaoCemTempoBrowApplication.class, args);
        System.out.println("Acesse: http://localhost:8080\nSwagger UI: /swagger-ui.html ");

    }

}
