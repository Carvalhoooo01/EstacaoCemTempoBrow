// src/main/java/br/unipar/programacaoweb/estacaocemtempobrow/repository/EstacaoRepository.java
package br.unipar.programacaoweb.estacaocemtempobrow.repository;

import br.unipar.programacaoweb.estacaocemtempobrow.model.EstacaoMonitoramento;
import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.statusEstacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface estacaoRepositor extends JpaRepository<EstacaoMonitoramento, Long> {
    List<EstacaoMonitoramento> findByStatus(statusEstacao status);
}