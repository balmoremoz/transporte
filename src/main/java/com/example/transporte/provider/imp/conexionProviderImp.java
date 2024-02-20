package com.example.transporte.provider.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.transporte.entities.ConexionEntity;
import com.example.transporte.provider.ConexionProvider;
import com.example.transporte.repository.ConexionRepo;
@Component
public class conexionProviderImp implements ConexionProvider{
	@Autowired
	ConexionRepo conexionRepo;
	
	public List<ConexionEntity>getAllConexiones(){
		
		return conexionRepo.findAll();
	}
}
