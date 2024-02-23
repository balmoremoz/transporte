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
	ciudades.forEach(ciudad => {
		$("#ciudadOrigen").append("<option>" + ciudad + "</option>");
		$("#ciudadDestino").append("<option>" + ciudad + "</option>");
	});
}
function mostrarRuta(response) {
	$("#ciudadOrigen").val("");
	$("#ciudadDestino").val("");
	let rutaOptimaHTML = "";

	response.forEach(ciudad => {
		rutaOptimaHTML += ciudad + " ";
	});

	$("#rutaOptima").html(rutaOptimaHTML);
}
