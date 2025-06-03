package br.unipar.programacaoweb.estacaocemtempobrow.configuration;

import br.unipar.programacaoweb.estacaocemtempobrow.model.Usuario;
import br.unipar.programacaoweb.estacaocemtempobrow.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;


    public SecurityFilter(TokenService tokenService,
                          UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    private String getToken(HttpServletRequest request)
            throws Exception {
        var token = request.getHeader("Authorization");
        if(token == null || token.isEmpty()) {
            throw new Exception("Token não encontrado!");
        }
        else if(!token.startsWith("Bearer ")) {
            throw new Exception("Token inválido!");
        }

        return token.replace("Bearer ", "");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = this.getToken(request);
            var subject = tokenService.getSubjectByToken(token);

            Usuario user = usuarioRepository.findEntityByUsername(subject)
                    .orElseThrow(() -> new Exception("Usuário não encontrado"));

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());

            SecurityContextHolder
                    .getContext().setAuthentication(authentication);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}