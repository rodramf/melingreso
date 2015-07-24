package com.meli.ingreso

import grails.rest.RestfulController

class ClimaDiaController extends RestfulController<ClimaDia>{
	//static namespace = 'v1'
	
	static responseFormats = ['json']
	
	ClimaDiaController() {
		super(ClimaDia)
	}

	@Override
	def index(final Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond listAllResources(params), [metadata: [total: listAllResources(params).totalCount, psize: params.max, offset: params.offset?:0]]
	}
}
