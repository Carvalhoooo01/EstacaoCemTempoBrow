package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

public class SensorController
{

    private EstacaoService estacaoService;

    public SensorController(EstacaoService estacaoService)
    {

        this.estacaoService = estacaoService;

    }

    @GetMapping("/media_sensores")
    public ResponseEntity<Float> media_sensores(String nome_estacao, String tipo_sensor)
    {

        Estacao estacao = estacaoService.buscar_por_nome(nome_estacao);

        if((tipo_sensor == null || tipo_sensor.isEmpty()) || (nome_estacao == null || nome_estacao.isEmpty()))
        {

            return ResponseEntity.badRequest().build();

        }

        if(estacaoService.existe_igual(estacao))
        {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }

        List<Sensor> sensores = estacao.getSensores();

        float soma = 0;
        int contador = 0;

        for(Sensor sensor : sensores)
        {

            soma += sensor.getValor();

            contador++;

        }

        float media = soma / contador;

        return ResponseEntity.status(HttpStatus.OK).body(media);

    }

}
