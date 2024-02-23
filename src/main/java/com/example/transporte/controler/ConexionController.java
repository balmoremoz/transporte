package com.example.transporte.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transporte.entities.ConexionEntity;
import com.example.transporte.provider.ConexionProvider;

@RestController
public class ConexionController {
	@Autowired
	ConexionProvider conexionProvider;

	@GetMapping("/getAllConexiones")
	public ResponseEntity<List<ConexionEntity>> getAllConexiones() {
		return new ResponseEntity<>(conexionProvider.getAllConexiones(), HttpStatus.OK);
	}
	
	@GetMapping("/getCiudades")
	public ResponseEntity<List<String>> getCiudades(){
		return new ResponseEntity<>(conexionProvider.getCiudades(), HttpStatus.OK);
	}
	
	@GetMapping("/costoMenor")
	public ResponseEntity<List<List<String>>> getCostoMenor(@RequestParam(defaultValue = "ciudadOrigen")String ciudadOrigen){
		return new ResponseEntity<>(conexionProvider.getRutaCostoMenor(ciudadOrigen),HttpStatus.OK);
	}

	@GetMapping("/costoMenorOrigenDestino")
	public ResponseEntity<List<String>>getCostosMenorOrigenDestino(@RequestParam(defaultValue = "ciudadOrigen") String ciudadOrigen,
			@RequestParam(defaultValue = "ciudadDestino")String ciudadDestino){
			return new ResponseEntity<>(conexionProvider.getRutaCostoMenor(ciudadOrigen, ciudadDestino),HttpStatus.OK);
	}
	
}
