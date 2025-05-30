package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.LeituraRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService
{

    private LeituraRepository leituraRepository;

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

}
