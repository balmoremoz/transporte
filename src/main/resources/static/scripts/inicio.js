getCiudades();

function imprimirRutaOptima() {
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://localhost:8081/costoMenorOrigenDestino?ciudadOrigen=" + $("#ciudadOrigen").val() + "&ciudadDestino=" + $("#ciudadDestino").val(),
		success: function(response) {
			console.log(response)
			mostrarRuta(response);
		},
		error: function(xhr) {
			console.error(xhr.responseText);
		}
	});
}

function getCiudades() {
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://localhost:8081/getCiudades",
		success: function(response) {
			console.log(response)
			asignarCiudades(response);
		},
		error: function(xhr) {
			console.error(xhr.responseText);
		}
	});
}
function asignarCiudades(ciudades) {
	$("#ciudadOrigen").append("<option>" + "ciudad de origen..." + "</option>");
	$("#ciudadDestino").append("<option>" + "ciudad de destino..." + "</option>");
	ciudades.forEach(ciudad => {
		$("#ciudadOrigen").append("<option>" + ciudad + "</option>");
		$("#ciudadDestino").append("<option>" + ciudad + "</option>");
	});
}
function mostrarRuta(response) {
	$("#ciudadOrigen").val("ciudad de origen...");
	$("#ciudadDestino").val("ciudad de destino...");
	let rutaOptimaHTML = "";
	let cont = 0;
	response.forEach(ciudad => {
		if (cont < response.length-1) {
			rutaOptimaHTML += ciudad + " -> " + response[cont + 1]+" ";
			cont++;
		}

	});

	$("#rutaOptima").html(rutaOptimaHTML);
}
