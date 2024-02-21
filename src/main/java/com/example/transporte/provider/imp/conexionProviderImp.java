package com.example.transporte.provider.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
		List<ConexionEntity> conexiones=conexionRepo.findAll();
		return conexiones;
	}
	
	private HashSet<String>  getCiudades(List<ConexionEntity> conexiones){
		HashSet<String> ciudades=new HashSet<>();
		
        for (ConexionEntity conexion : conexiones) {
            ciudades.add(conexion.getCiudadOrigen());
            ciudades.add(conexion.getCiudadDestino());
        }

        return ciudades;
	}
	
	private int [][] generarMatriz(int numCiudades , List<ConexionEntity> conexiones, HashSet<String> ciudades){
		int [][] costoConexiones=new int[numCiudades][numCiudades];
		ArrayList<String> ciudadesArray=(ArrayList<String>)ciudades.stream().collect(Collectors.toList());
		//iniciar todo a 0
        for (int i = 0; i < numCiudades; i++) {
            for (int j = 0; j < numCiudades; j++) {
                costoConexiones[i][j] = 0;
            }
        }
        
        for (ConexionEntity conexion : conexiones) {
        
        	int fila =ciudadesArray.indexOf(conexion.getCiudadOrigen());
        	int columna =ciudadesArray.indexOf(conexion.getCiudadDestino());
            costoConexiones[fila][columna] = conexion.getCosto();
        }
        return costoConexiones;
	}
	
	public List<String> getRutaCostoMenor() {
		 List<String> ruta=new ArrayList<>();
		 List<ConexionEntity>conexiones=conexionRepo.findAll();
		 HashSet<String> ciudades =getCiudades(conexiones);
		 int numCiudades=ciudades.size();
		 
		 int[][] costosConexiones=generarMatriz(numCiudades,conexiones,ciudades);
		 
		 return ruta;
	}
}
