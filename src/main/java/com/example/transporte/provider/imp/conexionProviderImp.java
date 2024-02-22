package com.example.transporte.provider.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.transporte.entities.ConexionEntity;
import com.example.transporte.provider.ConexionProvider;
import com.example.transporte.repository.ConexionRepo;

@Component
public class conexionProviderImp implements ConexionProvider {
	@Autowired
	ConexionRepo conexionRepo;

	// -----------DIJKSTRA-------------
	public int costo[] = new int[10];
	

	public List<ConexionEntity> getAllConexiones() {
		List<ConexionEntity> conexiones = conexionRepo.findAll();
		return conexiones;
	}

	private HashSet<String> getCiudades(List<ConexionEntity> conexiones) {
		HashSet<String> ciudades = new HashSet<>();

		for (ConexionEntity conexion : conexiones) {
			ciudades.add(conexion.getCiudadOrigen());
			ciudades.add(conexion.getCiudadDestino());
		}

		return ciudades;
	}

	private int[][] generarMatriz(int numCiudades, List<ConexionEntity> conexiones, HashSet<String> ciudades) {
		int[][] costoConexiones = new int[numCiudades][numCiudades];
		ArrayList<String> ciudadesArray = (ArrayList<String>) ciudades.stream().collect(Collectors.toList());
		// iniciar todo a 0 los viajes que no pueden existir y a 99 los costes desconocidos
		for (int i = 0; i < numCiudades; i++) {
			for (int j = 0; j < numCiudades; j++) {
				costoConexiones[i][j] = 99;
				costoConexiones[i][i]=0;
			}
		}
		//se da valor a las conexiones existentes en la base de datos
		for (ConexionEntity conexion : conexiones) {
			int fila = ciudadesArray.indexOf(conexion.getCiudadOrigen());
			int columna = ciudadesArray.indexOf(conexion.getCiudadDestino());
			costoConexiones[fila][columna] = conexion.getCosto();
			costoConexiones[columna][fila] = conexion.getCosto();
		}
		return costoConexiones;
	}

	public List<String> getRutaCostoMenor() {
		List<String> ruta = new ArrayList<>();
		List<ConexionEntity> conexiones = conexionRepo.findAll();
		HashSet<String> ciudades = getCiudades(conexiones);
		
		int numCiudades = ciudades.size();
		int[][] costosConexiones = generarMatriz(numCiudades, conexiones, ciudades);

		calc(9, 2,costosConexiones,ciudades);
		return ruta;
	}

	public void calc(int numCiudades, int origen,int [][] costosConexiones,HashSet<String> ciudades) {
		ArrayList<String> ciudadesArray = (ArrayList<String>) ciudades.stream().collect(Collectors.toList());
		List<List<String>> ruta = new ArrayList<>();
		int flag[] = new int[numCiudades];
		int i, posicionMinima = 0, k, c, minimo;
	
		for (i = 0; i < numCiudades; i++) {
			flag[i] = 0;
			this.costo[i] = costosConexiones[origen][i];
		}
		c = 2;
		while (c < numCiudades) {
			minimo = 99;
			for (k = 0; k < numCiudades; k++) {
				
				if (this.costo[k] < minimo && flag[k] != 1) {// si es menor que 99 lo toma como el costo minimo definitivo(de momento)
					minimo = this.costo[k];
					posicionMinima = k;
				}
			}

			flag[posicionMinima] = 1;
			c++;
			for (k = 0; k < numCiudades; k++) {
				if (this.costo[posicionMinima] + costosConexiones[posicionMinima][k] < this.costo[k] && flag[k] != 1)
					this.costo[k] = this.costo[posicionMinima] + costosConexiones[posicionMinima][k]; //suma los costos que son menores que los menores registrados anterior mente
			}
		}
		for (i = 0; i < numCiudades; i++) {
			if (i != origen) {
				System.out.println(ciudadesArray.get(origen)+" "+"  "+ciudadesArray.get(i)+" "+costo[i] + "\t");
			}
		}
	
	}
}
