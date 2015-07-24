package com.meli.ingreso

import grails.transaction.Transactional

@Transactional
class ClimaService {

	def obtnerPronostico() {
		def i = 1
		Pronostico pronostico = Pronostico.instance
		pronostico.init()
		while (i <= 3600) {
			pronostico.dia = i
			
			
			
			//if(i == 90){
							println "--------------------------"
							println "Iniciando " + pronostico.dia
//				Planeta.findAll().each{
//					it.log
//				}
			//}
			
			
			
//			println "--------------------------"
//			println "Iniciando " + pronostico.dia
			
			alineacionPlanetas(Planeta.findAll())
			solEnTriangulo(Planeta.findAll())
			i++
		}

		println "&&&&&&&&&&&&&&&&&&&&"
		println "Resultado Optimo ${pronostico.optimo}"
		println "Resultado Sequia ${pronostico.sequia}"
		println "Resultado Lluvia ${pronostico.lluvia}"
		println "Resultado Lluvia 2 ${pronostico.lluvia2}"
		println "Resultado Lluvia 3 ${pronostico.lluvia3}"
		println "Mayor Perimetro Lluvia ${pronostico.perimetroTotal}"
		println "Dia Lluvia ${pronostico.diaMayorLluvia}"
	}

	//Permite determinar si los planetas de una colección estan alineados entre si
	def alineacionPlanetas(List<Planeta> planetas){

		if(planetas.size() >= 3){
			//utilizo 2 planetas para generar una recta
			def punto1 = planetas[0]
			def punto2 = planetas[1]
			def punto3 = planetas[2]
			def perimetroTotal

			if(puntoEnRecta(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY)){
				Pronostico.instance.optimo++
				
//				println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
//				println "ALINEACION"
//				punto1.log
//				punto2.log
//				punto3.log

				//si alinean los 3 planetas verificamos si alinean con el sol tomando solo 2 puntos
				if(puntoEnRecta(punto1.posX, punto1.posY, punto2.posX, punto2.posY, 0, 0)){
					Pronostico.instance.sequia++

					//          println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					//          println "ALINEA SOL"

				}
			}
			
		}
	}

	def solEnTriangulo(List<Planeta> planetas){
		if(planetas.size() >= 3){
			//utilizo 2 planetas para generar una recta
			def punto1 = planetas[0]
			def punto2 = planetas[1]
			def punto3 = planetas[2]
			Pronostico pronostico = Pronostico.instance
			def perimetroTotal = 0

			if(puntoEnTriangulo(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY, 0, 0)){

				perimetroTotal = perimetro(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY)
				pronostico.verificarDiaMayorLluvia(perimetroTotal)
				Pronostico.instance.lluvia++

				println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				println "SOL EN TRIANGULO"
				punto1.log
				punto2.log
				punto3.log
				println "PERIMETRO TOTAL ${perimetroTotal}"

			}

			if(pointInTriangle(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY, 0, 0)){
				println "**"
				println "TAMBIEN ENTRA ACA - VERIFICADO"
				
				Pronostico.instance.lluvia2++
			}
			if(pointInTriangle2(punto1.posX, punto1.posY, punto2.posX, punto2.posY, punto3.posX, punto3.posY, 0, 0)){
				println "**"
				println "333333  --- TAMBIEN ENTRA ACA - VERIFICADO"
				
				Pronostico.instance.lluvia3++
			}

		}

	}

	//arma una recta con 1 y 2 y determina si 3 pertenece a la misma
	def puntoEnRecta(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3){
		
		if(x1==0 && x2==0 && x3==0 ){
			return true
		}		
		def puntoY = Math.round((((y2-y1)/(x2-x1)) * (x3 - x1)) + y1)
		def a = puntoY -y3
		//margen de error
		if(Math.abs(a) <= 50){
//			println "ALINEA"
//			println "PUNTO Y ${puntoY} y punto Y3 ${y3} --- ${Math.abs(puntoY - y3)}"
			return true
		}
	}

	def puntoEnTriangulo(Double x1, Double y1,Double x2, Double y2, Double x3, Double y3, Double x, Double y){
		def denominator = ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3))

		def a = ((y2 - y3)*(x - x3) + (x3 - x2)*(y - y3)) / denominator
		def b = ((y3 - y1)*(x - x3) + (x1 - x3)*(y - y3)) / denominator
		def c = 1 - a - b

		return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1

	}

	def perimetro(Double x1, Double y1, Double x2,Double y2, Double x3, Double y3){
		def perimetroTotal = 0
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(x1, y1, x2, y2)
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(x1, y1, x3, y3)
		perimetroTotal = perimetroTotal + distanciaEntrePuntos(x2, y2, x3, y3)
		return perimetroTotal
	}

	def distanciaEntrePuntos(Double x1, Double y1, Double x2, Double y2){
		return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2))
	}



	def puntoEnRectaByLeandro(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3){
		def a
		def b

		a = (x1 - x2) / (x3 - x1)


		b =  (y1 - y2) / (y3 - y1)


		if(a == b){
			return true
		}

	}

	def side(Double x1, Double y1, Double x2, Double y2, Double x, Double y)
	{
		return (y2 - y1)*(x - x1) + (-x2 + x1)*(y - y1);
	}

	def pointInTriangle(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3, Double x, Double y)
	{
		def checkSide1 = side(x1, y1, x2, y2, x, y) >= 0
		def checkSide2 = side(x2, y2, x3, y3, x, y) >= 0
		def checkSide3 = side(x3, y3, x1, y1, x, y) >= 0
		return checkSide1 && checkSide2 && checkSide3
	}
	
	def pointInTriangle2(Double x1,Double y1, Double x2, Double y2,Double x3, Double y3,Double  x,Double y)
	{
	 def denominator = (x1*(y2 - y3) + y1*(x3 - x2) + x2*y3 - y2*x3);
	 def t1 = (x*(y3 - y1) + y*(x1 - x3) - x1*y3 + y1*x3) / denominator;
	 def t2 = (x*(y2 - y1) + y*(x1 - x2) - x1*y2 + y1*x2) / -denominator;
	 def s = t1 + t2;
	 
	 return 0 <= t1 && t1 <= 1 && 0 <= t2 && t2 <= 1 && s <= 1;
	}


}
