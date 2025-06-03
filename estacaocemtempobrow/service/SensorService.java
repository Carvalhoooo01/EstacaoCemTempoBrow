package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService
{

    @Autowired
    SensorRepository sensorRepository;

    public Sensor salvar(Sensor sensor)
    {

        return sensorRepository.save(sensor);

    }

    public List<Sensor> listar_todos()
    {

        return sensorRepository.findAll();

    }

    public void excluir(Long id)
    {

        sensorRepository.deleteById(id);

    }

    public Sensor buscar_por_id(Long id)
    {

        return sensorRepository.findSensorById(id);

    }

    public boolean existe_igual(Sensor sensor)
    {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Sensor> example = Example.of(sensor, matcher);

        return sensorRepository.exists(example);

    }

    public Sensor pegar_ou_criar(Sensor sensor)
    {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Sensor> example = Example.of(sensor, matcher);

        Sensor existente = sensorRepository.findOne(example).orElse(null);

        if(existente != null)
        {

            return existente;

        }
        else
        {

            return sensorRepository.save(sensor);

        }

    }

    public List<Sensor> salvar_inexitentes(List<Sensor> sensores)
    {

        List<Sensor> new_sensor = new ArrayList<>();

        for(Sensor sensor : sensores)
        {

            new_sensor.add(pegar_ou_criar(sensor));

        }

        return new_sensor;

    }

}
