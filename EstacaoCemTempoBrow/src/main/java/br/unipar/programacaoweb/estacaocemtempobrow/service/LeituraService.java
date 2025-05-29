package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.LeituraRepository;
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

}
