package com.meli.jsonRender

import static groovy.transform.TypeCheckingMode.SKIP
import grails.converters.JSON
import grails.rest.render.RenderContext
import grails.rest.render.json.JsonCollectionRenderer
import groovy.transform.CompileStatic

import org.codehaus.groovy.grails.web.mime.MimeType

@CompileStatic
class CustomJsonCollectionRenderer extends JsonCollectionRenderer{

    CustomJsonCollectionRenderer(Class componentType) {
        super(componentType)
    }

    public CustomJsonCollectionRenderer(Class componentType, MimeType... mimeTypes) {
        super(componentType, mimeTypes)
    }

    @CompileStatic(SKIP)
    @Override
    protected void renderJson(object, RenderContext context) {
        log.debug(object)
        log.debug(object.size())
        log.debug(object.getTotalCount())
	
		
        Map tObject = ['data':object.toArray()]
        if(context.arguments?.metadata) {
            tObject['metadata'] = context.arguments.metadata
        }
        super.renderJson(tObject,context)
    }
}