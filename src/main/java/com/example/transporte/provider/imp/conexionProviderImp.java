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
		// iniciar todo a 0 los viajes que no pueden existir y a 99 los costes
		// desconocidos
		for (int i = 0; i < numCiudades; i++) {
			for (int j = 0; j < numCiudades; j++) {
				costoConexiones[i][j] = 99;
				costoConexiones[i][i] = 0;
			}
		}
		// se da valor a las conexiones existentes en la base de datos
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

		calc(9, 2, costosConexiones, ciudades);
		return ruta;
	}

	public void calc(int numCiudades, int origen, int[][] costosConexiones, HashSet<String> ciudades) {
		ArrayList<String> ciudadesArray = new ArrayList<>(ciudades);
		List<List<String>> rutas = new ArrayList<>();
		int[] flags = new int[numCiudades];
		int i, minPosicion = 0, k, c, minimo;

		// Inicializar el arreglo de costos
		costo = new int[numCiudades];
		for (i = 0; i < numCiudades; i++) {
			flags[i] = 0;
			costo[i] = costosConexiones[origen][i];
		}

		c = 2;
		while (c < numCiudades) {
			minimo = Integer.MAX_VALUE;
			for (k = 0; k < numCiudades; k++) {
				if (costo[k] < minimo && flags[k] != 1) {
					minimo = costo[k];
					minPosicion = k;
				}
			}

			flags[minPosicion] = 1;
			c++;
			for (k = 0; k < numCiudades; k++) {
				if (costo[minPosicion] + costosConexiones[minPosicion][k] < costo[k] && flags[k] != 1) {
					costo[k] = costo[minPosicion] + costosConexiones[minPosicion][k];
					// Inicializar la lista de rutas si es necesario
					if (rutas.size() <= k) {
						rutas.add(new ArrayList<>());
					}
					// Guardar la ruta
					List<String> ruta;
					if (minPosicion < rutas.size()) {
						// Si ya hay una lista en esa posición, simplemente obténla y agrega la ciudad
						// actual
						ruta = new ArrayList<>(rutas.get(minPosicion));
						ruta.add(ciudadesArray.get(k));
						rutas.set(minPosicion, ruta);
					} else {
						// Si no hay una lista en esa posición, crea una nueva lista y agrégala a rutas
						ruta = new ArrayList<>();
						ruta.add(ciudadesArray.get(k));
						rutas.add(ruta);
					}

					ruta.add(ciudadesArray.get(k));
					rutas.set(k, ruta);
				}
			}
		}

		// Mostrar el costo mínimo y la ruta
		for (i = 0; i < numCiudades; i++) {
			if (i != origen) {
				System.out.println(
						"Costo desde " + ciudadesArray.get(origen) + " a " + ciudadesArray.get(i) + ": " + costo[i]);
				System.out.println("Ruta: " + ciudadesArray.get(origen) + " " + rutas.get(i));
			}
		}

	}
}
