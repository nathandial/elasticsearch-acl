package com.searchly.rest.resources.cluster.node.stats

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
@Path("/")
class RestNodesStatsResource extends RestBaseResource {

    @GET
    @Path("_cluster/nodes/stats")
    @Secure(Role.ADMIN)
    public void clusterNodes(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
