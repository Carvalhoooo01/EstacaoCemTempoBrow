package br.unipar.programacaoweb.estacaocemtempobrow.repository;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Role;
import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.PermissaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{

    Role findByPermissao(PermissaoEnum permissao);
    
}
