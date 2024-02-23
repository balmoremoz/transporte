package com.example.transporte.provider;

import java.util.List;

import com.example.transporte.entities.ConexionEntity;

public interface ConexionProvider {
	List<ConexionEntity> getAllConexiones();
	
	List<List<String>> getRutaCostoMenor(String ciudadOrigen);
	
	List<String>getRutaCostoMenor(String ciudadOrigen, String ciudadDestino);
	
	List<String>getCiudades();
}
