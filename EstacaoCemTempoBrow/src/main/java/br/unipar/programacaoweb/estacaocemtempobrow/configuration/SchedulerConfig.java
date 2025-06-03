package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Estacao;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Leitura;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Role;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Usuario;
import br.unipar.programacaoweb.estacaocemtempobrow.service.EstacaoService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.LeituraService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.RoleService;
import br.unipar.programacaoweb.estacaocemtempobrow.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class SchedulerConfig
{

    @Autowired
    LeituraService leituraService;

    @Autowired
    RoleService roleService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EstacaoService estacaoService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)//Em milisegundos
    public void gerar_leitura_auto()
    {

        Leitura leitura = leituraService.gerar_leitura_auto();

        System.out.println("Leitura gerada aleatoriamente:\n" + leitura);

    }

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    public void veficar_status()
    {

        List<Estacao> estacoes = estacaoService.checar_inatividade();

        if (estacoes != null)
        {

            for (Estacao estacao : estacoes)
            {

                System.out.println("Estação " + estacao.getId() + " inativa há 2 minutos ISSO É INACEITÁVEL!");

                System.out.println("Enviando serviço de manutenção para a estação " + estacao.getId());

            }

        }

    }
/*
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void salvar_tudo_auto()
    {

        roleService.salvar();

        List<Role> roleList = roleService.listar_todos();

        for (Role role : roleList)
        {

            System.out.println(role.getPermissao().getDescricao());

        }

        usuarioService.salvar();

        List<Usuario> usuarioList = usuarioService.listar_todos();

        for(Usuario usuario : usuarioList)
        {

            System.out.println(usuario.getUsername() + " | " + usuario.getAuthorities());

        }

    }*/

}
