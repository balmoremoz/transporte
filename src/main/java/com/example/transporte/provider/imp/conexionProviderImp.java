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
	
	public List<String>getCiudades(){
		HashSet<String> ciudades = new HashSet<>();
		List<ConexionEntity> conexiones=getAllConexiones();
		
		for (ConexionEntity conexion : conexiones) {
			ciudades.add(conexion.getCiudadOrigen());
			ciudades.add(conexion.getCiudadDestino());
		}

		return  (ArrayList<String>) ciudades.stream().collect(Collectors.toList());
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

	public List<List<String>> getRutaCostoMenor(String ciudadOrigen) {
		List<ConexionEntity> conexiones = conexionRepo.findAll();
		HashSet<String> ciudades = getCiudades(conexiones);
		List<String> ciudadesArray = new ArrayList<>(ciudades);

		int numCiudades = ciudades.size();
		int[][] costosConexiones = generarMatriz(numCiudades, conexiones, ciudades);

		return calcularTodasLasRutas(ciudades.size(), ciudadesArray.indexOf(ciudadOrigen), costosConexiones, ciudades);
	}

	public List<String> getRutaCostoMenor(String ciudadOrigen, String ciudadDestino) {
		List<ConexionEntity> conexiones = conexionRepo.findAll();
		HashSet<String> ciudades = getCiudades(conexiones);
		List<String> ciudadesArray = new ArrayList<>(ciudades);

		int numCiudades = ciudades.size();
		int[][] costosConexiones = generarMatriz(numCiudades, conexiones, ciudades);

		return calcularRutaOrigenDestino(ciudadesArray.indexOf(ciudadOrigen), ciudadesArray.indexOf(ciudadDestino),
				ciudades.size(), ciudades, costosConexiones);
	}

	public List<String> calcularRutaOrigenDestino(int origen, int destino, int numCiudades, HashSet<String> ciudades,
			int[][] costosConexiones) {

		List<String> ciudadesArray = new ArrayList<>(ciudades);
		List<String> rutaMinima = new ArrayList<>();
		int[] flag = new int[numCiudades];
		int[] padre = new int[numCiudades]; // Para almacenar los nodos padres de cada nodo en el camino mínimo

		for (int i = 0; i < numCiudades; i++) {
			flag[i] = 0;
			padre[i] = -1; // Inicializar los padres como -1
		}

		costo = new int[numCiudades];
		for (int i = 0; i < numCiudades; i++) {
			costo[i] = 99;
		}
		costo[origen] = 0;

		int c = 1;
		while (c < numCiudades) {
			int minimo = 99;
			int posicionMinima = -1;

			for (int k = 0; k < numCiudades; k++) {
				if (costo[k] < minimo && flag[k] != 1) {
					minimo = costo[k];
					posicionMinima = k;
				}
			}

			flag[posicionMinima] = 1;
			c++;

			if (posicionMinima == destino) {
				break; // Si ya hemos llegado al destino, terminamos el bucle
			}

			for (int k = 0; k < numCiudades; k++) {
				if (costosConexiones[posicionMinima][k] != 0
						&& costo[posicionMinima] + costosConexiones[posicionMinima][k] < costo[k]) {
					costo[k] = costo[posicionMinima] + costosConexiones[posicionMinima][k];
					padre[k] = posicionMinima; // Actualizar el nodo padre
				}
			}
		}

		int nodoActual = destino;
		while (nodoActual != -1) {
			rutaMinima.add(0, ciudadesArray.get(nodoActual));
			nodoActual = padre[nodoActual];
		}

		return rutaMinima;
	}

	public List<List<String>> calcularTodasLasRutas(int numCiudades, int origen, int[][] costosConexiones,
			HashSet<String> ciudades) {

		List<String> ciudadesArray = new ArrayList<>(ciudades);
		List<List<String>> rutas = new ArrayList<>();
		int[] flag = new int[numCiudades];
		int[] padre = new int[numCiudades]; // Para almacenar los nodos padres de cada nodo en el camino mínimo

		for (int i = 0; i < numCiudades; i++) {
			flag[i] = 0;
			padre[i] = -1; // Inicializar los padres como -1
		}

		costo = new int[numCiudades];
		for (int i = 0; i < numCiudades; i++) {
			costo[i] = 99;
		}
		costo[origen] = 0;

		int c = 1;
		while (c < numCiudades) {
			int minimo = 99;
			int posicionMinima = -1;

			for (int k = 0; k < numCiudades; k++) {
				if (costo[k] < minimo && flag[k] != 1) {
					minimo = costo[k];
					posicionMinima = k;
				}
			}

			flag[posicionMinima] = 1;
			c++;

			for (int k = 0; k < numCiudades; k++) {
				if (costosConexiones[posicionMinima][k] != 0
						&& costo[posicionMinima] + costosConexiones[posicionMinima][k] < costo[k]) {
					costo[k] = costo[posicionMinima] + costosConexiones[posicionMinima][k];
					padre[k] = posicionMinima; // Actualizar el nodo padre
				}
			}
		}

		for (int i = 0; i < numCiudades; i++) {
			if (i != origen) {
				List<String> path = new ArrayList<>();
				int destino = i;
				while (destino != origen) {
					path.add(0, ciudadesArray.get(destino)); // Agregar ciudad al principio de la lista
					destino = padre[destino]; // Retroceder al nodo padre
				}
				path.add(0, ciudadesArray.get(origen)); // Agregar la ciudad de origen al principio de la lista
				rutas.add(path);
			}
		}
		return rutas;
	}
}
