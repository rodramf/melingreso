package com.meli.ingreso
import static org.springframework.http.HttpStatus.*


class PronosticoController {
	def climaService
	
	static allowedMethods = [index: "GET", generate: "POST"]
	
	static responseFormats = ['json']
	
    def index() { 
		respond climaService.obtnerPronostico(false)
	}
	
	def generate(){
		def result = climaService.obtnerPronostico(true)
		if(result){
			respond result
		}else{
			def msg = [:]
			msg["error"] = "Ocurrió un error al procesar la generación del pronóstico"
			respond msg, [status: UNPROCESSABLE_ENTITY]
		}
	}
}
