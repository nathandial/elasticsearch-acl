package com.searchly.rest.resources.core.mlt

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
 * @author ferhat
 */
@Path("/")
class RestMoreLikeThisResource extends RestBaseResource {
    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}/{id}/_mlt")
    public void postMoreLikeThis(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/{type}/{id}/_mlt")
    public void getMoreLikeThis(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }
}
