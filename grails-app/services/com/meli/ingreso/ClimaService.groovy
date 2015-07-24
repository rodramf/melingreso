package com.meli.ingreso

import grails.transaction.Transactional

@Transactional
class ClimaService {
	private static EPSILON= 0.001
	private static EPSILON_SQUARE = EPSILON*EPSILON
	
	@Transactional
	def obtnerPronostico(boolean generate) {
		def conf = Clima.get(1)
		def planetas = Planeta.findAll()
		def climaDia

		if(planetas.size() == 3){

			def i = 1

			Pronostico pronostico = Pronostico.instance
			pronostico.init()

			while (i <= conf?.cantidadDias) {
				//				println "--------------------------"
				//				println "Iniciando " + pronostico.dia


				pronostico.dia = i

				if(alineacionPlanetas(planetas, conf?.margenErrorKm)){
					if(alineacionPlanetasSol(planetas, conf?.margenErrorKm)){
						pronostico.setPronosticoDia(pronostico.SEQUIA, null)
					}else{
						pronostico.setPronosticoDia(pronostico.OPTIMO, null)
					}
				} else{
					if(solEnTriangulo(planetas)){
						def perimetroTotal = perimetroPlanetas(planetas)
						pronostico.setPronosticoDia(pronostico.LLUVIA, perimetroTotal)
					}else{
						pronostico.setPronosticoDia(pronostico.NORMAL, null)
					}
				}
				
				if(generate){
					climaDia = ClimaDia.findByDia(pronostico.dia)
					if(!climaDia){
						climaDia = new ClimaDia(dia: pronostico.dia)
					}	
					climaDia.clima = pronostico.diaClima
					
					climaDia.validate()
					if (climaDia.hasErrors()) {
						transactionStatus.setRollbackOnly()
						return false
					}
					
					climaDia.save flush:true
				}
				
				i++
			}

			return pronostico.mapResult()
		}
	}

	//Permite determinar si los planetas de una colección estan alineados entre si
	def alineacionPlanetas(List<Planeta> planetas, int margenError){

		def punto1 = planetas[0]
		def punto2 = planetas[1]
		def punto3 = planetas[2]

		if(puntoEnRecta(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY, margenError)){
			return true

		}
		return false

	}

	def alineacionPlanetasSol(List<Planeta> planetas, int margenError){


		//si alinean los 3 planetas verificamos si alinean con el sol tomando solo 2 puntos

		def punto1 = planetas[0]
		def punto2 = planetas[1]

		if(puntoEnRecta(punto1.posX, punto1.posY, punto2.posX, punto2.posY, 0, 0, margenError)){
			return true
		}

		return false
	}

	def solEnTriangulo(List<Planeta> planetas){

		def punto1 = planetas[0]
		def punto2 = planetas[1]
		def punto3 = planetas[2]

		Pronostico pronostico = Pronostico.instance

		if(accuratePointInTriangle(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY, 0, 0)){
			return true
		}

		return false
	}

	//arma una recta con 1 y 2 y determina si 3 pertenece a la misma
	def puntoEnRecta(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3, int margenError){

		if(x1==0 && x2==0 && x3==0 ){
			return true
		}
		def puntoY = Math.round((((y2-y1)/(x2-x1)) * (x3 - x1)) + y1)
		def a = puntoY -y3
		//margen de error
		if(Math.abs(a) <= margenError){
			//			println "ALINEA"
			//			println "PUNTO Y ${puntoY} y punto Y3 ${y3} --- ${Math.abs(puntoY - y3)}"
			return true
		}

		return false
	}

	def puntoEnTriangulo(Double x1, Double y1,Double x2, Double y2, Double x3, Double y3, Double x, Double y){
		def denominator = ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3))

		def a = ((y2 - y3)*(x - x3) + (x3 - x2)*(y - y3)) / denominator
		def b = ((y3 - y1)*(x - x3) + (x1 - x3)*(y - y3)) / denominator
		def c = 1 - a - b

		return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1

	}

	def perimetroPlanetas(planetas){
		def punto1 = planetas[0]
		def punto2 = planetas[1]
		def punto3 = planetas[2]

		def perimetroTotal = 0
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(punto1.posX, punto1.posY, punto2.posX, punto2.posY)
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(punto1.posX, punto1.posY, punto3.posX, punto3.posY)
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(punto2.posX, punto2.posY, punto3.posX, punto3.posY)
		return perimetroTotal
	}

	def distanciaEntrePuntos(Double x1, Double y1, Double x2, Double y2){
		return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2))
	}


	def side(Double x1,Double y1, Double x2, Double y2, Double  x,Double y)
	{
		return (y2 - y1)*(x - x1) + (-x2 + x1)*(y - y1)
	}

	def naivePointInTriangle(Double x1,Double y1, Double x2, Double y2,Double x3, Double y3,Double  x,Double y)
	{
		def checkSide1 = side(x1, y1, x2, y2, x, y) >= 0
		def checkSide2 = side(x2, y2, x3, y3, x, y) >= 0
		def checkSide3 = side(x3, y3, x1, y1, x, y) >= 0
		return checkSide1 && checkSide2 && checkSide3
	}

	def pointInTriangleBoundingBox(Double x1,Double y1, Double x2, Double y2,Double x3, Double y3,Double  x,Double y)
	{
		def xMin= Math.min(x1, Math.min(x2, x3)) - EPSILON
		def xMax= Math.max(x1, Math.max(x2, x3)) + EPSILON
		def yMin= Math.min(y1, Math.min(y2, y3)) - EPSILON
		def yMax= Math.max(y1, Math.max(y2, y3)) + EPSILON

		if ( x < xMin || xMax < x || y < yMin || yMax < y )
			return false
		else
			return true
	}

	def distanceSquarePointToSegment(Double x1,Double y1, Double x2, Double y2,Double  x,Double y)
	{
		def p1_p2_squareLength= (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1)
		def dotProduct = ((x - x1)*(x2 - x1) + (y - y1)*(y2 - y1)) / p1_p2_squareLength
		if ( dotProduct < 0 )
		{
			return (x - x1)*(x - x1) + (y - y1)*(y - y1)
		}
		else if ( dotProduct <= 1 )
		{
			def p_p1_squareLength = (x1 - x)*(x1 - x) + (y1 - y)*(y1 - y)
			return p_p1_squareLength - dotProduct * dotProduct * p1_p2_squareLength
		}
		else
		{
			return (x - x2)*(x - x2) + (y - y2)*(y - y2);
		}
	}

	def accuratePointInTriangle(Double x1,Double y1, Double x2, Double y2,Double x3, Double y3,Double  x,Double y)
	{
		if (! pointInTriangleBoundingBox(x1, y1, x2, y2, x3, y3, x, y))
			return false

		if (naivePointInTriangle(x1, y1, x2, y2, x3, y3, x, y))
			return true

		if (distanceSquarePointToSegment(x1, y1, x2, y2, x, y) <= EPSILON_SQUARE)
			return true
		if (distanceSquarePointToSegment(x2, y2, x3, y3, x, y) <= EPSILON_SQUARE)
			return true
		if (distanceSquarePointToSegment(x3, y3, x1, y1, x, y) <= EPSILON_SQUARE)
			return true

		return false
	}
}
