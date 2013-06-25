package com.searchly.rest.resources.indices.flush

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("{index}/_flush")
class RestFlushResource extends RestBaseResource {

    @POST
    @Secure(Role.MEMBER)
    def flushPost(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }

    @GET
    @Secure(Role.MEMBER)
    def flushGet(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }
}
