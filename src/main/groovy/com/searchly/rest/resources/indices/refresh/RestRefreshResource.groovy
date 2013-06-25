package com.searchly.rest.resources.indices.refresh

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
class RestRefreshResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/_refresh")
    @Produces("application/json")
    def refreshGetIndex(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }

    @Secure(Role.MEMBER)
    @GET
    @Path("_refresh")
    @Produces("application/json")
    def refreshGet(@Suspended final AsyncResponse response) {
        response.resume(dummyOk())
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/_refresh")
    @Produces("application/json")
    def refreshPostIndex(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            response.resume(dummyOk())
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("_refresh")
    @Produces("application/json")
    def refreshPost(@Suspended final AsyncResponse response) {
        response.resume(dummyOk())
    }
}
