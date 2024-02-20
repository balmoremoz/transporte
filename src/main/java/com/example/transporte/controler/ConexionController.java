package com.example.transporte.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transporte.entities.ConexionEntity;
import com.example.transporte.provider.ConexionProvider;

@RestController
public class ConexionController {
	@Autowired
	ConexionProvider conexionProvider;
	
	@GetMapping("/getAllConexiones")
	public ResponseEntity<List<ConexionEntity>> getAllConexiones(){
		return new ResponseEntity<>(conexionProvider.getAllConexiones(),HttpStatus.OK);
	}
}
