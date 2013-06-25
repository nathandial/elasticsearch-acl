package com.searchly.rest.resources.cluster.health

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("")
class RestHealthResource extends RestBaseResource {

    @Secure(Role.ADMIN)
    @GET
    @Path("/_cluster/health")
    public void health(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @Secure(Role.ADMIN)
    @GET
    @Path("/_cluster/health/{index}")
    public void healthWithIndex(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
