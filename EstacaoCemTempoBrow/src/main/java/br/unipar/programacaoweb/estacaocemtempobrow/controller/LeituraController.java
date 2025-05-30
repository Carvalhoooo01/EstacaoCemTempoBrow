package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/leitura")
public class LeituraController
{

    /*Permitir que o usuário recupere, via endpoint, as leituras de um determinado tipo de sensor de uma estação, filtrando por período de tempo (data início e data fim).
    Um endpoint que retorne média, mínima e máxima das leituras de um determinado tipo de sensor, em uma estação específica, em um intervalo de datas.
    Recuperar a média das leituras de todos os sensores de determinada estação.*/

    private LeituraService leituraService;
    private EstacaoService estacaoService;

    public LeituraController(LeituraService leituraService, EstacaoService estacaoService)
    {

        this.leituraService = leituraService;
        this.estacaoService = estacaoService;

    }

    @GetMapping("/filtrada")
    public ResponseEntity<List<Leitura>> filtrar_leituras(String nome_estacao, String tipo_sensor, Date data_inicio, Date data_fim)
    {

        Estacao estacao = estacaoService.buscar_por_nome(nome_estacao);

        if((data_inicio == null || data_fim == null) || (data_inicio.after(data_fim)) || (tipo_sensor == null || tipo_sensor.isEmpty()) || (nome_estacao == null || nome_estacao.isEmpty()))
        {

            return ResponseEntity.badRequest().build();

        }

        if(estacaoService.existe_igual(estacao))
        {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }

        List<Sensor> sensores = estacao.getSensores();

        List<Leitura> leituras = new ArrayList<>();

        for(Sensor sensor : sensores)
        {

            if(sensor.getTipo().equals(tipo_sensor))
            {

                List<Leitura> leituras_sensor = sensor.getHistoricoList();

                for(Leitura leitura : leituras_sensor)
                {

                    Date data_leitura = leitura.getData_leitura();

                    if(data_leitura.before(data_fim) && data_leitura.after(data_inicio))
                    {

                        leituras.add(leitura);

                    }

                }

            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(leituras);

    }

    @GetMapping("/media_leituras")
    public ResponseEntity<List<Float>> media_valores(String nome_estacao, String tipo_sensor, Date data_inicio, Date data_fim)
    {

        /*Estacao estacao = estacaoService.buscar_por_nome(nome_estacao);

        if((data_inicio == null || data_fim == null) || (data_inicio.after(data_fim)) || (tipo_sensor == null || tipo_sensor.isEmpty()) || (nome_estacao == null || nome_estacao.isEmpty()))
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

            if(sensor.getTipo().equals(tipo_sensor))
            {

                List<Leitura> leituras_sensor = sensor.getHistoricoList();

                for(Leitura leitura : leituras_sensor)
                {

                    Date data_leitura = leitura.getData_leitura();

                    if(data_leitura.before(data_fim) && data_leitura.after(data_inicio))
                    {

                        media_valores.add(leitura.getValor_leitura());

                    }

                }

            }

        }*/

        List<Leitura> leituras = filtrar_leituras(nome_estacao, tipo_sensor, data_inicio, data_fim).getBody();

        List<Float> media_valores = new ArrayList<>();

        for(Leitura leitura : leituras)
        {

            media_valores.add(leitura.getValor_leitura());

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

        List<Float> media = new ArrayList<>();

        media.add(media_valores.getFirst());
        media.add(media_valores.get(list_meio));
        media.add(media_valores.getLast());

        return ResponseEntity.status(HttpStatus.OK).body(media);

    }

}
