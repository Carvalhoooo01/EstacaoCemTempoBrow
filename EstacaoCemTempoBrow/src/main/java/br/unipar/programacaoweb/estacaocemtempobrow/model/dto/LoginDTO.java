package br.unipar.programacaoweb.estacaocemtempobrow.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDTO
(

        @NotNull @NotEmpty @NotBlank String username,
        @NotNull @NotEmpty @NotBlank String password

){ }
