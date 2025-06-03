package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import br.unipar.programacaoweb.estacaocemtempobrow.model.dto.LoginDTO;
import br.unipar.programacaoweb.estacaocemtempobrow.model.dto.LoginResponse;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService
{

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public AuthorizationService(UsuarioRepository usuarioRepository, TokenService tokenService)
    {

        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        return usuarioRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () ->
                        new UsernameNotFoundException("Usuario nao encontrado!")

        );

    }

    public LoginResponse doLogin(LoginDTO loginDTO)
    {

        var user = loadUserByUsername(loginDTO.username());

        return new LoginResponse(
                user.getUsername(),
                tokenService.gerarToken(user)
        );

    }

}
