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
@Path("/{index}/_optimize")
@Produces("application/json")
class RestOptimizeResource extends RestBaseResource {

    @GET
    @Secure(Role.MEMBER)
    def optimizeGet(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }

    @POST
    @Secure(Role.MEMBER)
    def optimizePost(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }
}