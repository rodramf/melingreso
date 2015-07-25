import com.meli.ingreso.ClimaDia
import com.meli.ingreso.Planeta
import com.meli.jsonRender.CustomJsonCollectionRenderer
import com.meli.jsonRender.CustomJsonRenderer

// Place your Spring DSL code here
beans = {
	jsonClimaDiaCollectionRenderer(CustomJsonCollectionRenderer, ClimaDia)
	jsonClimaDiaRenderer(CustomJsonRenderer, ClimaDia)
	jsonPlanetaCollectionRenderer(CustomJsonCollectionRenderer, Planeta)
	jsonPlanetaRenderer(CustomJsonRenderer, Planeta)
}
