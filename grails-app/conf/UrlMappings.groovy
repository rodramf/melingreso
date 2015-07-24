class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
		
		"/api/pronostico" (controller:'pronostico', action:'index')
		"/api/pronostico/generate" (controller:'pronostico', action:'generate')
		
		"/api/planetas" (resources:'planeta')
		"/api/clima/settings" (resource:'clima'){
			id = 1
		}
		"/api/clima" (resources:'climaDia'){
			"/planetas" (resources:'planeta')
		}
	}
}
