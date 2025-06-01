package br.unipar.programacaoweb.estacaocemtempobrow.service;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Role;
import br.unipar.programacaoweb.estacaocemtempobrow.model.Usuario;
import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.PermissaoEnum;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.RoleRepository;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService
{

    UsuarioRepository usuarioRepository;
    RoleRepository roleRepository;

    UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository)
    {

        this.usuarioRepository = usuarioRepository;
        this.roleRepository  = roleRepository;

    }

    public void salvar()
    {

        List<String> username = new ArrayList<String>();
        List<String> password = new ArrayList<String>();
        List<Role> role = new ArrayList<Role>();

        username.add("admin");
        password.add("admin");
        role.add(roleRepository.findByPermissao(PermissaoEnum.ADMIN));

        username.add("user");
        password.add("user");
        role.add(roleRepository.findByPermissao(PermissaoEnum.USER));

        for(int i = 0; i < role.size(); i++)
        {

            if(usuarioRepository.findByUsername(username.get(i)) != null)
            {

                return;

            }

            Usuario usuario = new Usuario();

            List<Role> _role = new ArrayList<Role>();

            _role.add(role.get(i));

            usuario.setUsername(username.get(i));
            usuario.setPassword(password.get(i));
            usuario.setRoleList(_role);

            usuarioRepository.save(usuario);

        }

    }

    public List<Usuario> listar_todos()
    {

        return usuarioRepository.findAll();

    }

}
