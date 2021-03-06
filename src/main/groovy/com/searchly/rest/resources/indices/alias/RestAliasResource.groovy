package com.searchly.rest.resources.indices.alias

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
@Path("/")
class RestAliasResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @GET
    @Path("{index}/_aliases")
    public void getAliases(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = account(request)
        if (isAuthorized(account, index)) {
            execute(request, response, index, account)
        }
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("_aliases")
    public void aliases(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def rawRequestBody = request.getAttribute(PARSED_JSON_BODY)
        execute(request, response, null, "")
    }

}
