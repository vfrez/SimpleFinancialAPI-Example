package com.example.credideb.service;

import com.example.credideb.configuration.exception.ApiErrorException;
import com.example.credideb.model.ContaEntity;
import com.example.credideb.model.TransacaoEntity;
import com.example.credideb.repository.ContaRepository;
import com.example.credideb.repository.TransacaoRepository;
import com.example.credideb.service.mapper.ContaMapper;
import com.example.model.ContaRequestDTO;
import com.example.model.ContaResponseDTO;
import com.example.model.SaldoResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public ContaResponseDTO createConta(ContaRequestDTO contaRequestDTO) {
        log.info("Criando conta para agência: {}", contaRequestDTO.getAgencia());

        try {
            ContaEntity contaEntity = saveConta(contaRequestDTO);
            log.info("Criada conta {} para agência: {}", contaEntity.getNumeroConta(), contaEntity.getAgencia());

            return ContaMapper.mapToContaContaResponse(contaEntity);
        }
        catch (Exception e) {
            log.error("Erro ao criar conta: {}", e.getMessage(), e);
            throw new ApiErrorException("Erro ao criar conta: " + e.getMessage());
        }
    }

    public SaldoResponseDTO calculateContaSaldo(Integer agencia, Integer conta) {
        log.info("Calculando saldo da conta: {} e agência: {}", conta, agencia);

        BigDecimal saldoConta = getTransacoesAndCalculate(agencia, conta);
        log.info("Saldo calculado para conta: {} e agência: {} é {}", conta, agencia, saldoConta);

        return ContaMapper.mapToSaldoResponseDTO(agencia, conta, saldoConta);
    }


    @Transactional
    private ContaEntity saveConta(ContaRequestDTO contaRequestDTO) {
        Optional<Integer> nextNumeroConta = getNextNumeroContaByAgencia(contaRequestDTO);

        ContaEntity contaEntity = ContaMapper.mapToContaEntity(contaRequestDTO, nextNumeroConta.orElse(1));
        return contaRepository.save(contaEntity);
    }

    private Optional<Integer> getNextNumeroContaByAgencia(ContaRequestDTO contaRequestDTO) {
        return contaRepository.findNextNumeroContaByAgencia(contaRequestDTO.getAgencia());
    }

    @Transactional
    private BigDecimal getTransacoesAndCalculate(Integer agencia, Integer conta) {
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
