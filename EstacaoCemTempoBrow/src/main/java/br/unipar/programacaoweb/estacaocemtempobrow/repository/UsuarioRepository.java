package br.unipar.programacaoweb.estacaocemtempobrow.repository;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    Optional<UserDetails> findByUsernameIgnoreCase(String username);

    @Query("select u from Usuario u where lower(u.username) = lower(:username)")
    Optional<Usuario> findEntityByUsername(@Param("username") String username);

}
