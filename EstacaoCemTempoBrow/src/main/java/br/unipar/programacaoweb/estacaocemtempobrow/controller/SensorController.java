package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class SensorController
{

    private EstacaoService estacaoService;
    private SensorService sensorService;
    private LeituraService leituraService;

    public SensorController(EstacaoService estacaoService, SensorService sensorService, LeituraService leituraService)
    {

        this.estacaoService = estacaoService;
        this.sensorService = sensorService;
        this.leituraService = leituraService;

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

    @PostMapping("/salvar")
    public ResponseEntity<Sensor> salvar(@RequestBody Sensor sensor)
    {

        try
        {

            if(sensorService.existe_igual(sensor))
            {

                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            }

            Sensor sensor_salvo = sensorService.salvar(sensor);

            leituraService.salvar_inexitentes(sensor_salvo.getHistoricoList());

            return ResponseEntity.ok(sensor_salvo);

        }
        catch(Exception e)
        {

            e.printStackTrace();

            return ResponseEntity.badRequest().build();

        }

    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir_por_id(@PathVariable Long id)
    {

        try
        {

            Sensor sensor = sensorService.buscar_por_id(id);

            if(sensor == null)
            {

                return ResponseEntity.badRequest().build();

            }

            sensorService.excluir(sensor.getId());

            return ResponseEntity.noContent().build();

        }
        catch (Exception e)
        {

            e.printStackTrace();

            return ResponseEntity.badRequest().build();

        }

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Sensor> editar_por_id(@PathVariable Long id, @RequestBody Sensor sensor)
    {

        try
        {

            Sensor sensor_salvo = sensorService.buscar_por_id(id);

            if(sensor_salvo == null)
            {

                return ResponseEntity.badRequest().build();

            }

            if(!sensor.equals(sensor_salvo) && sensorService.existe_igual(sensor))
            {

                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            }

            sensor_salvo.setNome(sensor.getNome());
            sensor_salvo.setTipo(sensor.getTipo());
            sensor_salvo.setValor(sensor.getValor());
            sensor_salvo.setHistoricoList(sensor.getHistoricoList());

            return ResponseEntity.ok(sensorService.salvar(sensor));

        }
        catch (Exception e)
        {

            e.printStackTrace();

            return ResponseEntity.badRequest().build();

        }

    }

}
