package br.unipar.programacaoweb.estacaocemtempobrow.repository;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long>
{


}
