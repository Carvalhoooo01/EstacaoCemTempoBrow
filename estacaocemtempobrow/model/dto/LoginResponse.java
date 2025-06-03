package br.unipar.programacaoweb.estacaocemtempobrow.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.antlr.v4.runtime.misc.NotNull;

public record LoginResponse
(

        @NotNull @NotEmpty @NotBlank String username,
        @NotNull @NotEmpty @NotBlank String token

) { }
