package com.example.credideb.repository;

import com.example.credideb.model.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, UUID> {

    Optional<ContaEntity> findByAgenciaAndNumeroConta(Integer agencia, Integer conta);

}
