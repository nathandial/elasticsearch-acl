package com.searchly.rest.resources.cluster.settings

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestClusterSettingsResource extends RestBaseResource {

    @GET
    @Path("_cluster/settings")
    @Secure(Role.ADMIN)
    public void getSettings(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @PUT
    @Path("_cluster/settings")
    @Secure(Role.ADMIN)
    public void putSettings(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
