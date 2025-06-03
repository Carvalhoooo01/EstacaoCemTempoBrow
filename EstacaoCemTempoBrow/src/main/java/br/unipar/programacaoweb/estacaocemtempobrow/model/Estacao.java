package br.unipar.programacaoweb.estacaocemtempobrow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "estacao", cascade = CascadeType.ALL)
    private List<Sensor> sensores;

    @Column(name = "data_instalacao", nullable = false)
    private LocalDate dataInstalacao = LocalDate.now();

    private boolean info_externa;

    // Para integração com API de clima (ponto extra)
    /*
    private Double temperaturaAtual;
    private Double umidadeAtual;
    private String condicaoClimatica;
    private String fonteDadosClimaticos;
    */

}