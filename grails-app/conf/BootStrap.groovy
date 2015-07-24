import com.meli.ingreso.Constantes
import com.meli.ingreso.Planeta

class BootStrap {

    def init = { servletContext ->
		if(Planeta.count == 0){
			new Planeta(nombre: 'Vulcanos', distanciaSol: 1000, sentido: Constantes.ANTIHORARIO, gradosPorDia: 2.5).save()
			new Planeta(nombre: 'Ferengi', distanciaSol: 500, sentido: Constantes.HORARIO, gradosPorDia: 0.5).save()
			new Planeta(nombre: 'Betasoide', distanciaSol: 2000, sentido: Constantes.HORARIO, gradosPorDia: 1.5).save()
		}
    }
    def destroy = {
    }
}
