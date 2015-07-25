package com.meli.ingreso

import grails.rest.RestfulController

class ClimaController extends RestfulController<Clima>{
	
	static allowedMethods = [update: "PUT"]
	
	static responseFormats = ['json']
	
	ClimaController() {
		super(Clima)
	}
	
}
