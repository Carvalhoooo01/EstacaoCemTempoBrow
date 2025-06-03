package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
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

            Estacao estacaoSalvo = estacao;

            if(estacaoSalvo == null || estacaoSalvo.getSensores() == null)
            {

                return ResponseEntity.badRequest().body(null);

            }

            for(Sensor sensor : estacaoSalvo.getSensores())
            {

                for(Leitura leitura : sensor.getHistoricoList())
                {

                    leitura.setUnidade(sensor);

                }

                sensor.setHistoricoList(leituraService.salvar_inexitentes(sensor.getHistoricoList()));

            }

            estacaoSalvo.setSensores(sensorService.salvar_inexitentes(estacaoSalvo.getSensores()));

            return ResponseEntity.ok(estacaoService.salvar(estacaoSalvo));

        }
        catch(Exception e)
        {

            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);

        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {

        Estacao estacao = estacaoService.buscar_por_id(id);

        if(estacao == null)
        {

            return ResponseEntity.notFound().build();

        }

        estacaoService.excluir(estacao);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Estacao> editar(@PathVariable Long id, @RequestBody Estacao estacao)
    {

        Estacao estacaoSalvo = estacaoService.buscar_por_id(id);

        if(estacaoSalvo == null)
        {

            return ResponseEntity.notFound().build();

        }

        if(!estacao.equals(estacaoSalvo) && estacaoService.existe_igual(estacaoSalvo))
        {

            return ResponseEntity.badRequest().build();

        }

        estacaoSalvo.setNome(estacao.getNome());
        estacaoSalvo.setSensores(estacao.getSensores());
        estacaoSalvo.setStatus(estacao.getStatus());
        estacaoSalvo.setLatitude(estacao.getLatitude());
        estacaoSalvo.setLongitude(estacao.getLongitude());
        estacaoSalvo.setDataInstalacao(estacao.getDataInstalacao());

        return ResponseEntity.ok(estacaoService.salvar(estacaoSalvo));

    }

}
