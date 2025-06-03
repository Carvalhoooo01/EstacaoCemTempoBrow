package br.unipar.programacaoweb.estacaocemtempobrow.model;

import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.PermissaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity

@Getter
@Setter
public class Role implements Serializable
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissaoEnum permissao;

    public Role() {}

}
