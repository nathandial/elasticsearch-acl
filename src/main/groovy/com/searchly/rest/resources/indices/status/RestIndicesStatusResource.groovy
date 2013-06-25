package com.searchly.rest.resources.indices.status

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestIndicesStatusResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/_status")
    public void status(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.ADMIN)
    @GET
    @Path("_status")
    public void allStatus(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
