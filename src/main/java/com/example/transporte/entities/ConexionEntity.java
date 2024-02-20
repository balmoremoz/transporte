package com.example.transporte.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@Entity(name = "ConexionEntity")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Table(name = "CONEXION")
public class ConexionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "CIUDADORIGEN")
	private String ciudadOrigen;
	@Column(name = "CIUDADDESTINO")
	private String ciudadDestino;
	@Column(name = "COSTO")
	private int costo;
}
