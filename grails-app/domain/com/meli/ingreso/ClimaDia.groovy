package com.meli.ingreso

class ClimaDia {
	Integer dia
	String clima
	Date dateCreated
	Date lastUpdated

    static constraints = {
		dia unique:true
    }
}
