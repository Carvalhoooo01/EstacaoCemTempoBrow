package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.LeituraRepository;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class LeituraService
{

    private LeituraRepository leituraRepository;
    private SensorRepository sensorRepository;
    private SensorService sensorService;

    public LeituraService(LeituraRepository leituraRepository, SensorRepository sensorRepository, SensorService sensorService)
    {

        this.leituraRepository = leituraRepository;
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;

    }

    public Leitura salvar(Leitura leitura)
    {

        return leituraRepository.save(leitura);

    }

    public void excluir(Leitura leitura)
    {

        leituraRepository.deleteById(leitura.getId());

    }

    public Leitura buscar_por_id(Long id)
    {

        return leituraRepository.findById(id).orElse(null);

    }

    public List<Leitura> buscar_todos()
    {

        return leituraRepository.findAll();

    }

    public boolean existe_igual(Leitura leitura)
    {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Leitura> example = Example.of(leitura, matcher);

        return leituraRepository.exists(example);

    }

    public void salvar_inexitentes(List<Leitura> leituras)
    {

        for(Leitura leitura : leituras)
        {

            if(!existe_igual(leitura))
            {

                leituraRepository.save(leitura);

            }

        }

    }

    public Leitura gerar_leitura_auto()
    {

        Long total = sensorRepository.count();

        if(total == 0)
        {

            return null;

        }

        Long id = Math.round(Math.random() * total);

        Sensor sensor = sensorService.buscar_por_id(id);

        Leitura leitura = new Leitura();

        leitura.setStatus_sensor(sensor.getStatus());
        leitura.setTipo_sensor(sensor.getTipo());
        leitura.setUnidade(sensor);
        leitura.setValor_leitura(sensor.getValor());
        leitura.setData_leitura(new Date());
        leitura.setEstacao(sensor.getEstacao());
        leitura.setMensagem("Gerada automaticamente");
        leitura.setInfo_externa(sensor.getEstacao().isInfo_externa());

        Leitura leitura_salva = leituraRepository.save(leitura);

        sensor.getHistoricoList().add(leitura_salva);

        sensorRepository.save(sensor);

        return leitura_salva;

    }

}
