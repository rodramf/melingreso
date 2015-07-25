package com.meli.ingreso

import grails.converters.JSON
import grails.rest.RestfulController

class PlanetaController extends RestfulController<Planeta>{
	
	static responseFormats = ['json']
	
	PlanetaController() {
		super(Planeta)
	}
	
	@Override
	protected Planeta queryForResource(Serializable id) {
		
		JSON.use("info")
		
		if(params.climaDiaId){
			Pronostico pronostico = Pronostico.instance
			pronostico.dia = params.climaDiaId.toInteger()
			JSON.use("details")
		}
		
		Planeta.where {
			id == id
		}.find()
	}
	

	@Override
	protected List<Planeta> listAllResources(Map params) {
		JSON.use("info")
		if(params.climaDiaId){
			Pronostico pronostico = Pronostico.instance
			pronostico.dia = params.climaDiaId.toInteger()
			JSON.use("details")
		}
		
		Planeta.list(params)
		def c = Planeta.createCriteria()
		def results = c.list (params) {}
	}
	
	@Override
	def index(final Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond listAllResources(params), [metadata: [total: listAllResources(params).totalCount, psize: params.max, offset: params.offset?:0]]
	}

}
