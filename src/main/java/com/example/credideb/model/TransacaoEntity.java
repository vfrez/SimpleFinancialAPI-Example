package com.example.credideb.model;

import com.example.credideb.model.converter.TipoTransacaoConverter;
import com.example.credideb.model.enumeration.TipoTransacaoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACAO")
public class TransacaoEntity {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "ID")
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_CONTA", nullable = false)
    private ContaEntity conta;

    @Column(nullable = false, name = "TIPO_TRANSACAO")
    @Convert(converter = TipoTransacaoConverter.class) // Não obrigatório se autoApply=true no conversor
    private TipoTransacaoEnum tipoTransacao;

    @Column(nullable = false, name = "VALOR")
    private BigDecimal valor;

    @Column(nullable = false,name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

}
