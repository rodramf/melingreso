package com.meli.ingreso

class PruebaController {
	def climaService

    def index() {
		climaService.obtnerPronostico()

	}
}
