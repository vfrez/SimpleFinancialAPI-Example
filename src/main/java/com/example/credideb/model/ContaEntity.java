package com.example.credideb.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONTA")
public class ContaEntity {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(nullable = false, name = "NOME")
    private String nome;

    @Column(nullable = false, name = "AGENCIA")
    private Integer agencia;

    @Column(nullable = false, name = "NUMERO_CONTA")
    private Integer numeroConta;

    @Column(nullable = false,name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

}
