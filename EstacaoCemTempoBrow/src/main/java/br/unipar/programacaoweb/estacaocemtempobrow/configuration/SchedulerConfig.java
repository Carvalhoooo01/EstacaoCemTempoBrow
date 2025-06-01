package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class SchedulerConfig
{

    @Autowired
    LeituraService leituraService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)//Em milisegundos
    public void gerar_leitura_auto()
    {

        Leitura leitura = leituraService.gerar_leitura_auto();

        System.out.println("Leitura gerada aleatoriamente:\n" + leitura);

    }

}
