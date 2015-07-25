package com.meli.ingreso

import java.util.Date;

class Planeta {
	String nombre
	Integer distanciaSol
	String sentido
	Double gradosPorDia
	Integer gradosOrigen = 0
	Date dateCreated
	Date lastUpdated

    static constraints = {
		nombre unique:true
    }
	
	static transients = ['posX','posY', 'vel', 'factorSentido', 'log']
	
	def getVel(){
		def grados = (factorSentido * gradosPorDia * Pronostico.instance.dia) + gradosOrigen 
		def radianes = (grados * Math.PI)/180
		return radianes
	}
	
	def getPosX(){
		return Math.round(distanciaSol * Math.cos(vel))
	}
	
	def getPosY(){
		return Math.round(distanciaSol * Math.sin(vel))
	}
	
	def getFactorSentido(){
		if(sentido.equals(Pronostico.HORARIO)){
			return -1
		}else{
			return 1
		}
	}
	
	def getLog(){
		println "PLANETA ${nombre}"
		println "grados por dia ${gradosPorDia}"
		println "sentido ${sentido}"
		println "distancia sol ${distanciaSol}"
		println "radianes ${vel}"
		println "posicion ${posX} X ${posY} Y"
		println "*************************"
	}
	
}
