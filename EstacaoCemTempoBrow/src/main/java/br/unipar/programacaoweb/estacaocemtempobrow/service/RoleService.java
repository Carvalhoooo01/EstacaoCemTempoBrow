package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Role;
import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.PermissaoEnum;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService
{

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository)
    {

        this.roleRepository = roleRepository;

    }

    public void salvar()
    {

        List<PermissaoEnum> roles = new ArrayList<PermissaoEnum>();

        roles.add(PermissaoEnum.ADMIN);
        roles.add(PermissaoEnum.USER);

        for(PermissaoEnum permissao : roles)
        {

            if(roleRepository.findByPermissao(permissao) == null)
            {

                Role role = new Role();

                role.setPermissao(permissao);

                roleRepository.save(role);

            }

        }

    }

    public List<Role> listar_todos()
    {

        return roleRepository.findAll();

    }

}
