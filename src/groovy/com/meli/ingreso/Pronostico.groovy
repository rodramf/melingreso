package com.meli.ingreso

import java.util.List;

@Singleton
class Pronostico {
	public static String HORARIO = "HORARIO"
	public static String ANTIHORARIO = "ANTIHORARIO"
	//public static List TODAS = [this.HORARIO, this.ANTIHORARIO]
	public static String LLUVIA = "LLUVIA"
	public static String SEQUIA = "SEQUIA"
	public static String OPTIMO = "OPTIMO"
	public static String NORMAL = "NORMAL"
	public static String CLIMAS = [this.LLUVIA, this.SEQUIA, this.OPTIMO, this.NORMAL]
	
	def dia
	def diaClima
	def diasSequia
	def diasOptimo
	def diasLluvia
	def diasNormal
	def perimetroTotal
	def diaMayorLluvia
	
	def init(){
		dia = 0
		diasSequia = 0
		diasOptimo = 0
		diasLluvia = 0
		diasNormal = 0
		perimetroTotal = 0
		diaMayorLluvia = 0
	}
	
	def verificarDiaMayorLluvia(perimetro){
		if(perimetro > this.perimetroTotal){
			this.perimetroTotal = perimetro
			this.diaMayorLluvia = this.dia
	   }
	}
	
	def setPronosticoDia(String clima, Double perimetro){
		switch(clima){
			case LLUVIA:
				diasLluvia++
				diaClima = LLUVIA
				verificarDiaMayorLluvia(perimetro)
			break
			case SEQUIA:
				diasSequia++
				diaClima = SEQUIA
			break
			case OPTIMO:
				diasOptimo++
				diaClima = OPTIMO
			break
			default:
				diasNormal++
				diaClima = NORMAL
			break
		}
	}
	
	def mapResult(){
		def map = [:]
		map["diasSequia"] = diasSequia
		map["diasOptimo"] = diasOptimo
		map["diasLluvia"] = diasLluvia
		map["diasNormal"] = diasNormal
		map["perimetroTotal"] = Math.round(perimetroTotal)
		map["diaMayorLluvia"] = diaMayorLluvia
		return map
	}
}
