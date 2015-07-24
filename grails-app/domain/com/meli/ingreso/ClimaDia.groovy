package com.meli.ingreso

class ClimaDia {
	Integer dia
	String clima

    static constraints = {
		dia unique:true
    }
}
