package com.searchly.rest.resources.core.bulk

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestBulkResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @POST
    @Path("_bulk")
    public void postBulk(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @Secure(Role.MEMBER)
    @PUT
    @Path("_bulk")
    public void putBulk(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/_bulk")
    public void postBulkWithIndex(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @PUT
    @Path("{index}/_bulk")
    public void putBulkWithIndex(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}/_bulk")
    public void postBulkWithIndexAndType(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @PUT
    @Path("{index}/{type}/_bulk")
    public void putBulkWithIndexAndType(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }
}