package com.tutorial.racksemanalservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackSemanalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String rutCliente;

    @Column(nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(nullable = false)
    private LocalDateTime fechaHoraFin;

    @Column(nullable = false)
    private boolean activa;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCancelacion;

    @Column(length = 500)
    private String notas;

    @Column(nullable = false)
    private String estado;
}


