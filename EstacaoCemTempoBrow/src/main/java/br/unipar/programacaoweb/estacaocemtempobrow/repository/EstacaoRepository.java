// src/main/java/br/unipar/programacaoweb/estacaocemtempobrow/repository/EstacaoRepository.java
package br.unipar.programacaoweb.estacaocemtempobrow.repository;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstacaoRepository extends JpaRepository<Estacao, Long>
{

    List<Estacao> findByStatus(String status);

    Estacao findByNome(String nome);

}