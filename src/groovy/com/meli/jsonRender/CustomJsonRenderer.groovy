package com.meli.jsonRender

import static groovy.transform.TypeCheckingMode.SKIP
import grails.rest.render.RenderContext
import grails.rest.render.json.JsonRenderer
import groovy.transform.CompileStatic

import org.codehaus.groovy.grails.web.mime.MimeType

@CompileStatic
class CustomJsonRenderer extends JsonRenderer{

    CustomJsonRenderer(Class componentType) {
        super(componentType)
    }

    public CustomJsonRenderer(Class componentType, MimeType... mimeTypes) {
        super(componentType, mimeTypes)
    }

    @CompileStatic(SKIP)
    @Override
    protected void renderJson(object, RenderContext context) {
       
		
        Map tObject = ['data':object]
        if(context.arguments?.metadata) {
            tObject['metadata'] = context.arguments.metadata
        }
        super.renderJson(tObject,context)
    }
}