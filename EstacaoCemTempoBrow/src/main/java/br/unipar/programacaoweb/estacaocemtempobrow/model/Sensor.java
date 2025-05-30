package br.unipar.programacaoweb.estacaocemtempobrow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sensor
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    private float valor;

    @ManyToOne
    @JoinColumn(name = "estacao_id")
    @JsonBackReference
    private Estacao estacao;

    @OneToMany
    List<Leitura> historicoList;

}
