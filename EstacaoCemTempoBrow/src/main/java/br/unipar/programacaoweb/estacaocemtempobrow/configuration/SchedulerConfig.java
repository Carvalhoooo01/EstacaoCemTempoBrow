package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig
{

    @Scheduled(fixedRate = 5000)//Em milisegundos
    public void exemplo()
    {

        //codigo que schwdule vai executar

    }

}
