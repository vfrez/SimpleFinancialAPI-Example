package com.example.credideb.api.controller;

import com.example.api.ContaApi;
import com.example.credideb.service.ContaService;
import com.example.model.ContaRequestDTO;
import com.example.model.ContaResponseDTO;
import com.example.model.SaldoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContaController implements ContaApi {

    @Autowired
    private ContaService contaService;

    @Override
    public ResponseEntity<ContaResponseDTO> createConta(ContaRequestDTO contaRequestDTO) {
        String contaResponseMessage = contaService.createConta(contaRequestDTO);

        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setMensagem(contaResponseMessage);

        return ResponseEntity.ok().body(contaResponseDTO);
    }

    @Override
    public ResponseEntity<SaldoResponseDTO> getSaldoConta(Integer agencia, Integer conta) {
        SaldoResponseDTO saldoResponseDTO = contaService.calculateContaSaldo(agencia, conta);

        return ResponseEntity.ok().body(saldoResponseDTO);
    }
}
