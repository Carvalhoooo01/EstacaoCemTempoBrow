package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

        float soma = 0;

        for(Leitura leitura : leituras)
        {

            media_valores.add(leitura.getValor_leitura());

            soma += leitura.getValor_leitura();

        }

        List<Float> media = new ArrayList<>();

        media.add(Collections.min(media_valores));
        media.add(soma / media_valores.size());
        media.add(Collections.max(media_valores));

        return ResponseEntity.status(HttpStatus.OK).body(media);

    }

    @PostMapping("/salvar")
    public ResponseEntity<Leitura> salvar(@RequestBody Leitura leitura)
    {

        Leitura leituraSalvo = leitura;

        if(leituraSalvo == null)
        {

            return ResponseEntity.badRequest().build();

        }

        return ResponseEntity.ok(leituraService.salvar(leituraSalvo));

    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir_por_id(@PathVariable Long id)
    {

        Leitura leitura_excluir = leituraService.buscar_por_id(id);

        if(leitura_excluir == null)
        {

            return ResponseEntity.notFound().build();

        }

        leituraService.excluir(leitura_excluir);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Leitura> editar_por_id(@PathVariable Long id, @RequestBody Leitura leitura)
    {

        Leitura leitura_editar = leituraService.buscar_por_id(id);

        if(leitura_editar == null)
        {

            return ResponseEntity.notFound().build();

        }

        if(!leitura_editar.equals(leitura) && leituraService.existe_igual(leitura))
        {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }

        leitura_editar.setTipo_sensor(leitura.getTipo_sensor());
        leitura_editar.setData_leitura(leitura.getData_leitura());
        leitura_editar.setValor_leitura(leitura.getValor_leitura());
        leitura_editar.setUnidade(leitura.getUnidade());
        leitura_editar.setValor_leitura(leitura.getValor_leitura());
        leitura_editar.setInfo_externa(leitura.isInfo_externa());
        leitura_editar.setStatus_sensor(leitura.getStatus_sensor());
        leitura_editar.setMensagem(leitura.getMensagem());

        return ResponseEntity.ok(leituraService.salvar(leitura_editar));

    }

    @GetMapping("/listar")
    public List<Leitura> listar_todos()
    {

        return leituraService.buscar_todos();

    }

}
