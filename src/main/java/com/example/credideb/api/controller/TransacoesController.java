package com.example.credideb.api.controller;

import com.example.api.TransacoesApi;
import com.example.credideb.service.TransacoesService;
import com.example.model.TransacoesHistoricoResponseDTO;
import com.example.model.TransacoesRequestDTO;
import com.example.model.TransacoesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class TransacoesController implements TransacoesApi {

    @Autowired
    private TransacoesService transacoesService;

    @Override
    public ResponseEntity<TransacoesResponseDTO> createTransacoes(TransacoesRequestDTO transacoesRequestDTO) {
        String transacoesResponseMessage = transacoesService.processarTransacoes(transacoesRequestDTO);

        TransacoesResponseDTO transacoesResponseDTO = new TransacoesResponseDTO();
        transacoesResponseDTO.setMensagem(transacoesResponseMessage);

        return ResponseEntity.ok().body(transacoesResponseDTO);
    }

    @Override
    public ResponseEntity<TransacoesHistoricoResponseDTO> getHiscoricoTransacoes(Integer agencia, Integer conta, LocalDate dataInicio, LocalDate dataFim) {
        TransacoesHistoricoResponseDTO hiscoricoTransacoes = transacoesService.getHistoricoTransacoes(agencia, conta, dataInicio, dataFim);

        return ResponseEntity.ok().body(hiscoricoTransacoes);
    }
}
