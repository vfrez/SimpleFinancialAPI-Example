package com.example.credideb.service;

import com.example.credideb.configuration.exception.ApiErrorException;
import com.example.credideb.configuration.exception.ApiValidationException;
import com.example.credideb.model.ContaEntity;
import com.example.credideb.model.TransacaoEntity;
import com.example.credideb.model.enumeration.TipoTransacaoEnum;
import com.example.credideb.repository.ContaRepository;
import com.example.credideb.repository.TransacaoRepository;
import com.example.model.TransacaoDTO;
import com.example.model.TransacoesHistoricoResponseDTO;
import com.example.model.TransacoesRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransacoesService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    public String processarTransacoes(TransacoesRequestDTO transacoesRequestDTO) {
        log.info("Processando transações. Quantidade de transações: {}", transacoesRequestDTO.getTransacoes().size());

        Optional<ContaEntity> contaEntity = searchConta(transacoesRequestDTO.getAgencia(), transacoesRequestDTO.getConta());
        try {
            processTransacaoValidation(transacoesRequestDTO, contaEntity);
            createTransacoes(transacoesRequestDTO, contaEntity.get());

            return String.format("Transações criadas para conta %s e agencia %s", transacoesRequestDTO.getAgencia(), transacoesRequestDTO.getConta());
        } catch (Exception e) {
            log.error("Erro ao processar transações: {}", e.getMessage(), e);
            throw new ApiErrorException("Erro ao processar transações " + e.getMessage());
        }
    }

    @Transactional
    private void createTransacoes(TransacoesRequestDTO transacoesRequestDTO, ContaEntity contaEntity) {
        List<TransacaoEntity> transacaoEntityList = transacoesRequestDTO.getTransacoes().stream()
                .map(transacao -> TransacaoEntity.builder()
                        .tipoTransacao(TipoTransacaoEnum.valueOf(transacao.getTipo().getValue()))
                        .valor(transacao.getValor())
                        .conta(contaEntity)
                        .dataCriacao(LocalDateTime.now())
                        .build())
                .toList();

        transacaoRepository.saveAll(transacaoEntityList);
    }

    private void processTransacaoValidation(TransacoesRequestDTO transacoesRequestDTO,
                                            Optional<ContaEntity> contaEntity) {
        if (contaEntity.isEmpty()) {
            throw new ApiValidationException(String.format("Conta %s e agencia %s nao encontradas", transacoesRequestDTO.getAgencia(), transacoesRequestDTO.getConta()));
        }
        if (transacoesRequestDTO.getTransacoes().isEmpty()) {
            throw new ApiValidationException("Não há transações para processar");
        }
    }

    @Transactional
    public TransacoesHistoricoResponseDTO getHistoricoTransacoes(Integer agencia,
                                                                 Integer conta,
                                                                 LocalDate dataInicio,
                                                                 LocalDate dataFim) {
        log.info("Buscando histórico de transações da conta: {} e agência: {}", conta, agencia);

        List<TransacaoEntity> transacaoHistory = transacaoRepository.findTrancacaoHistory(agencia,conta,
                LocalDateTime.of(dataInicio, LocalTime.MIN), LocalDateTime.of(dataFim, LocalTime.MAX));

        List<TransacaoDTO> transacoes = transacaoHistory.stream()
                .map(transacao -> {
                    TransacaoDTO transacaoDTO = new TransacaoDTO();
                    transacaoDTO.setData(transacao.getDataCriacao());
                    transacaoDTO.setTipo(TransacaoDTO.TipoEnum.valueOf(transacao.getTipoTransacao().name()));
                    transacaoDTO.setValor(transacao.getValor());
                    return transacaoDTO;
                })
                .collect(Collectors.toList());

        TransacoesHistoricoResponseDTO transacoesHistoricoResponseDTO = new TransacoesHistoricoResponseDTO();
        transacoesHistoricoResponseDTO.setTransacoes(transacoes);

        return transacoesHistoricoResponseDTO;
    }

    public Optional<ContaEntity> searchConta(Integer agencia, Integer conta) {
        log.info("Buscando conta: {} e agencia: {}", conta, agencia);

        return contaRepository.findByAgenciaAndNumeroConta(agencia, conta);
    }
}
