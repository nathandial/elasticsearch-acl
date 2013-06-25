package com.searchly.rest.resources.indices.exists

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.HEAD
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestExistsResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @HEAD
    @Path("{index}")
    public void indexExists(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @HEAD
    @Path("{index}/{type}")
    public void typeExists(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }
}
