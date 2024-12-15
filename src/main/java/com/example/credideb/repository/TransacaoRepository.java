package com.example.credideb.repository;

import com.example.credideb.model.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {

    List<TransacaoEntity> findByContaAgenciaAndContaNumeroConta(Integer agencia, Integer numeroConta);

    @Query("SELECT t FROM TransacaoEntity t " +
            "WHERE t.conta.numeroConta = :numeroConta " +
            "AND t.conta.agencia = :agencia " +
            "AND t.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<TransacaoEntity> findTrancacaoHistory(@Param("agencia") Integer agencia,
                                               @Param("numeroConta") Integer numeroConta,
                                               @Param("dataInicio") LocalDateTime dataInicio,
                                               @Param("dataFim") LocalDateTime dataFim);
}
