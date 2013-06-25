package com.searchly.rest.resources.cluster.state

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("_cluster/state")
class RestClusterStateResource extends RestBaseResource {
    @GET
    @Secure(Role.ADMIN)
    public void getState(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
