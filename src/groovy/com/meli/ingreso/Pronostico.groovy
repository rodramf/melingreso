package com.meli.ingreso

@Singleton
class Pronostico {
	def dia = 0
	def sequia = 0
	def optimo = 0
	def lluvia = 0
	def lluvia2 = 0
	def lluvia3 = 0
	def perimetroTotal = 0
	def diaMayorLluvia = 0
	
	def init(){
		dia = 0
		sequia = 0
		optimo = 0
		lluvia = 0
		lluvia2 = 0
		lluvia3 = 0
		perimetroTotal = 0
		diaMayorLluvia = 0
		
	}
	
	def verificarDiaMayorLluvia(perimetro){
		if(perimetro > this.perimetroTotal){
			this.perimetroTotal = perimetro
			this.diaMayorLluvia = this.dia
	   }
	}
}
