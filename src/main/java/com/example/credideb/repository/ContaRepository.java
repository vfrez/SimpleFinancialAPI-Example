package com.example.credideb.repository;

import com.example.credideb.model.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, UUID> {

    Optional<ContaEntity> findByAgenciaAndNumeroConta(Integer agencia, Integer conta);

    @Query("SELECT c.numeroConta + 1 FROM ContaEntity c WHERE c.agencia = :agencia ORDER BY c.numeroConta DESC LIMIT 1")
    Optional<Integer> findNextNumeroContaByAgencia(@Param("agencia") Integer agencia);

}
