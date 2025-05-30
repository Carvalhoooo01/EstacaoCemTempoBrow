package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.EstacaoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacaoService
{

    private EstacaoRepository estacaoRepository;

    public EstacaoService(EstacaoRepository estacaoRepository)
    {

        this.estacaoRepository = estacaoRepository;

    }

    public Estacao salvar(Estacao estacao)
    {

        return estacaoRepository.save(estacao);

    }

    public void excluir(Estacao estacao)
    {

        estacaoRepository.deleteById(estacao.getId());

    }

    public Estacao buscar_por_id(Long id)
    {

        return estacaoRepository.findById(id).orElse(null);

    }

    public List<Estacao> listar_todos()
    {

        return estacaoRepository.findAll();

    }

    public Estacao buscar_por_nome(String nome)
    {

        return estacaoRepository.findByNome(nome);

    }

    public boolean existe_igual(Estacao estacao)
    {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Estacao> example = Example.of(estacao, matcher);

        return estacaoRepository.exists(example);

    }

}
