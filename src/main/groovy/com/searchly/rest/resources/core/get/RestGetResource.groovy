package com.searchly.rest.resources.core.get

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 * @author ferhat
 */
@Path("/")
class RestGetResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/{type}/{id}")
    public void get(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @HEAD
    @Path("{index}/{type}/{id}")
    public void getHead(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/_mget")
    public void multiGet(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/_mget")
    public void postMultiGet(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/{type}/_mget")
    public void multiGetWithType(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}/_mget")
    public void postMultiGetWithType(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }
}
