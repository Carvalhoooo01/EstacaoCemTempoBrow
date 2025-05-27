package br.unipar.programacaoweb.estacaocemtempobrow.model;

import br.unipar.programacaoweb.estacaocemtempobrow.model.enums.statusEstacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacaoMonitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private statusEstacao status = statusEstacao.ATIVA;

    @Column(name = "data_instalacao", nullable = false)
    private LocalDate dataInstalacao = LocalDate.now();

    // Para integração com API de clima (ponto extra)
    private Double temperaturaAtual;
    private Double umidadeAtual;
    private String condicaoClimatica;
    private String fonteDadosClimaticos;

}