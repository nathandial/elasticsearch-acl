package com.searchly.rest.filters

import com.searchly.rest.resources.base.RestBaseResource
import groovy.json.JsonSlurper

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter

/**
 * @author ferhat
 */
class JsonRequestBodyParserFilter implements ContainerRequestFilter {
    @Override
    void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (containerRequestContext.getEntityStream().available()) {
            def body = new JsonSlurper().parse(new InputStreamReader(containerRequestContext.getEntityStream()))
            if (body) {
                containerRequestContext.setProperty(RestBaseResource.PARSED_JSON_BODY, body)
            }
        }
    }
}
