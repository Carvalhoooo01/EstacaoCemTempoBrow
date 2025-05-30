package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacao")
public class EstacaoController
{

    EstacaoService estacaoService;
    SensorService sensorService;
    LeituraService leituraService;

    public EstacaoController(EstacaoService estacaoService, SensorService sensorService, LeituraService leituraService)
    {

        this.estacaoService = estacaoService;
        this.sensorService = sensorService;
        this.leituraService = leituraService;

    }

    @GetMapping("/listar")
    public ResponseEntity<List<Estacao>> listar_todos()
    {

        return ResponseEntity.ok(estacaoService.listar_todos());

    }

    @PostMapping("/salvar")
    public ResponseEntity<Estacao> salvar(@RequestBody Estacao estacao)
    {

        try
        {

            Estacao estacaoSalvo = estacaoService.salvar(estacao);

            if(estacaoSalvo == null || estacaoSalvo.getSensores() == null)
            {

                return ResponseEntity.badRequest().body(null);

            }

            sensorService.salvar_inexitentes(estacaoSalvo.getSensores());

            for(Sensor sensor : estacaoSalvo.getSensores())
            {

                leituraService.salvar_inexitentes(sensor.getHistoricoList());

            }

            return ResponseEntity.ok(estacaoSalvo);

        }
        catch(Exception e)
        {

            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);

        }

    }

}
