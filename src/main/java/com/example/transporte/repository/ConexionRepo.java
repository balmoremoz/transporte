package com.example.transporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transporte.entities.ConexionEntity;

public interface ConexionRepo extends JpaRepository<ConexionEntity, Long> {
	List<ConexionEntity> findAll();
}
