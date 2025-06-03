package br.unipar.programacaoweb.estacaocemtempobrow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter

@NoArgsConstructor
@Entity
public class Leitura
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo_sensor;

    private String status_sensor;

    private float valor_leitura;

    @ManyToOne(cascade = CascadeType.ALL)
    private Sensor unidade;

    private Date data_leitura;

    private String mensagem;

    private boolean info_externa;

}
