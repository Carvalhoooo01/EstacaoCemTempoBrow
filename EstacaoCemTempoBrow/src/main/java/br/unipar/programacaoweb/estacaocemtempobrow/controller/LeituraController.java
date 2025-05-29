package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    }

}
