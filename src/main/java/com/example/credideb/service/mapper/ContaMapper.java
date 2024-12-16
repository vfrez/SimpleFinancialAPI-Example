package com.example.credideb.service.mapper;

import com.example.credideb.model.ContaEntity;
import com.example.model.ContaRequestDTO;
import com.example.model.ContaResponseDTO;
import com.example.model.SaldoResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaMapper {

    public static ContaEntity mapToContaEntity(ContaRequestDTO contaRequestDTO, Integer numeroConta) {
        return ContaEntity.builder()
                .nome(contaRequestDTO.getNome())
                .agencia(contaRequestDTO.getAgencia())
                .numeroConta(numeroConta)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public static ContaResponseDTO mapToContaContaResponse(ContaEntity contaEntity) {
        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setAgencia(contaEntity.getAgencia());
        contaResponseDTO.setConta(contaEntity.getNumeroConta());

        return contaResponseDTO;
    }

    public static SaldoResponseDTO mapToSaldoResponseDTO(Integer agencia, Integer conta, BigDecimal saldoConta) {
        SaldoResponseDTO saldoResponseDTO = new SaldoResponseDTO();
        saldoResponseDTO.setAgencia(agencia);
        saldoResponseDTO.setNumeroConta(conta);
        saldoResponseDTO.setSaldoAtual(saldoConta);

        return saldoResponseDTO;
    }
}
