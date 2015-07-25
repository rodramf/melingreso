import grails.converters.JSON

import com.meli.ingreso.Clima
import com.meli.ingreso.ClimaDia
import com.meli.ingreso.Planeta
import com.meli.ingreso.Pronostico

class BootStrap {

	def init = { servletContext ->
		if(Planeta.count == 0){
			new Planeta(nombre: 'Vulcanos', distanciaSol: 1000, sentido: Pronostico.ANTIHORARIO, gradosPorDia: 5).save()
			new Planeta(nombre: 'Ferengi', distanciaSol: 500, sentido: Pronostico.HORARIO, gradosPorDia: 1).save()
			new Planeta(nombre: 'Betasoide', distanciaSol: 2000, sentido: Pronostico.HORARIO, gradosPorDia: 3).save()
		}

		if(Clima.count == 0){
			new Clima(cantidadDias:3600, margenErrorKm:50).save()
		}


		JSON.registerObjectMarshaller(ClimaDia) {
			def returnArray = [:]
			returnArray['dia'] = it.dia
			returnArray['clima'] = it.clima
			returnArray['dateCreated'] = it.dateCreated
			returnArray['lastUpdated'] = it.lastUpdated
			return returnArray
		}

		JSON.registerObjectMarshaller(Clima) {
			def returnArray = [:]
			returnArray['cantidadDias'] = it.cantidadDias
			returnArray['margenErrorKm'] = it.margenErrorKm
			returnArray['dateCreated'] = it.dateCreated
			returnArray['lastUpdated'] = it.lastUpdated
			return returnArray
		}

		JSON.createNamedConfig('info') { JSONConfig ->
			JSONConfig.registerObjectMarshaller(Planeta) {
				def returnArray = [:]
				returnArray['id'] = it.id
				returnArray['nombre'] = it.nombre
				returnArray['distanciaSol'] = it.distanciaSol
				returnArray['sentido'] = it.sentido
				returnArray['gradosPorDia'] = it.gradosPorDia
				returnArray['gradosOrigen'] = it.gradosOrigen
				returnArray['dateCreated'] = it.dateCreated
				returnArray['lastUpdated'] = it.lastUpdated
				return returnArray
			}
		}

		JSON.createNamedConfig('details') { JSONConfig ->
			JSONConfig.registerObjectMarshaller(Planeta) {
				def returnArray = [:]
				returnArray['id'] = it.id
				returnArray['nombre'] = it.nombre
				returnArray['distanciaSol'] = it.distanciaSol
				returnArray['sentido'] = it.sentido
				returnArray['gradosPorDia'] = it.gradosPorDia
				returnArray['gradosOrigen'] = it.gradosOrigen
				returnArray['dateCreated'] = it.dateCreated
				returnArray['lastUpdated'] = it.lastUpdated
				returnArray['posicionX'] = it.posX
				returnArray['posicionY'] = it.posY
				

				return returnArray
			}
		}
	}
	def destroy = {
	}
}
