package br.unipar.programacaoweb.estacaocemtempobrow.controller;

import br.unipar.programacaoweb.estacaocemtempobrow.configuration.AuthorizationService;
import br.unipar.programacaoweb.estacaocemtempobrow.model.dto.LoginDTO;
import br.unipar.programacaoweb.estacaocemtempobrow.model.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{

    private final AuthorizationService authorizationService;

    public AuthenticationController(AuthorizationService authorizationService)
    {

        this.authorizationService = authorizationService;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO)
    {

        LoginResponse loginResponse = authorizationService.doLogin(loginDTO);

        return ResponseEntity.ok(loginResponse);

    }

}
