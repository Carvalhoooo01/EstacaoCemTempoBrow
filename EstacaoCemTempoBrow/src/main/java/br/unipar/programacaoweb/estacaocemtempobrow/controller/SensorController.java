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
        List<Float> media_valores = new ArrayList<>();

        for(Sensor sensor : sensores)
        {

            media_valores.add(sensor.getValor());

        }

        int n = media_valores.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (media_valores.get(j) > media_valores.get(j + 1)) {
                    Float temp = media_valores.get(j);
                    media_valores.set(j, media_valores.get(j + 1));
                    media_valores.set(j + 1, temp);
                }
            }
        }

        int list_meio = Math.round(media_valores.size() / 2);

        float media = media_valores.get(list_meio);

        return ResponseEntity.status(HttpStatus.OK).body(media);

    }

}
