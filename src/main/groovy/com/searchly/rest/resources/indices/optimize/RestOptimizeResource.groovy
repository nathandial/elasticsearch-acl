package com.searchly.rest.resources.indices.optimize

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestOptimizeResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/_optimize")
    @Produces("application/json")
    def optimizeGet(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/_optimize")
    @Produces("application/json")
    def optimizePost(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }
}