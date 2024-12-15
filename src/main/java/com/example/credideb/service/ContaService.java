package com.example.credideb.service;

import com.example.credideb.configuration.exception.ApiErrorException;
import com.example.credideb.model.ContaEntity;
import com.example.credideb.model.TransacaoEntity;
import com.example.credideb.repository.ContaRepository;
import com.example.credideb.repository.TransacaoRepository;
import com.example.model.ContaRequestDTO;
import com.example.model.SaldoResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public String createConta(ContaRequestDTO contaRequestDTO) {
        log.info("Criando conta: {} e agência: {}", contaRequestDTO.getConta(), contaRequestDTO.getAgencia());
        try {
            return saveConta(contaRequestDTO);
        }
        catch (Exception e) {
            log.error("Erro ao criar conta: {}", e.getMessage(), e);
            throw new ApiErrorException("Erro ao criar conta: " + e.getMessage());
        }
    }

    public SaldoResponseDTO calculateContaSaldo(Integer agencia, Integer conta) {
        log.info("Calculando saldo da conta: {} e agência: {}", conta, agencia);

        BigDecimal saldoConta = getTransactionAndCalculate(agencia, conta);

        log.info("Saldo calculado para conta: {} e agência: {} é {}", conta, agencia, saldoConta);

        return mapToSaldoResponseDTO(agencia, conta, saldoConta);
    }

    private SaldoResponseDTO mapToSaldoResponseDTO(Integer agencia, Integer conta, BigDecimal saldoConta) {
        SaldoResponseDTO saldoResponseDTO = new SaldoResponseDTO();
        saldoResponseDTO.setAgencia(agencia);
        saldoResponseDTO.setNumeroConta(conta);
        saldoResponseDTO.setSaldoAtual(saldoConta);

        return saldoResponseDTO;
    }

    @Transactional
    private String saveConta(ContaRequestDTO contaRequestDTO) {
        ContaEntity contaEntity = mapToContaEntity(contaRequestDTO);
        contaRepository.save(contaEntity);

        return String.format("Criadas conta %s e agencia %s com sucesso.", contaRequestDTO.getConta(), contaRequestDTO.getAgencia());
    }

    private ContaEntity mapToContaEntity(ContaRequestDTO contaRequestDTO) {
        ContaEntity contaEntity = ContaEntity.builder()
                .nome(contaRequestDTO.getNome())
                .agencia(contaRequestDTO.getAgencia())
                .numeroConta(contaRequestDTO.getConta())
                .dataCriacao(LocalDateTime.now())
                .build();
        return contaEntity;
    }

    @Transactional
    private BigDecimal getTransactionAndCalculate(Integer agencia, Integer conta) {
        List<TransacaoEntity> transacoesList = transacaoRepository.findByContaAgenciaAndContaNumeroConta(agencia, conta);

        return transacoesList.stream()
                .map(transacao -> {
                    if ("CREDITO".equalsIgnoreCase(transacao.getTipoTransacao().name())) {
                        return transacao.getValor(); // Crédito soma ao saldo
                    } else if ("DEBITO".equalsIgnoreCase(transacao.getTipoTransacao().name())) {
                        return transacao.getValor().negate(); // Débito subtrai do saldo
                    } else {
                        return BigDecimal.ZERO; // Ignorar transações desconhecidas
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
