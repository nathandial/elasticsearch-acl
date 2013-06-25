package com.searchly.rest.resources.cluster.reroute

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("_cluster/reroute")
class RestClusterRerouteResource extends RestBaseResource {
    @POST
    @Secure(Role.ADMIN)
    public void clusterNodes(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}

