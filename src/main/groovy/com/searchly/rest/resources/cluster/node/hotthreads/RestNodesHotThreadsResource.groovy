package com.searchly.rest.resources.cluster.node.hotthreads

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
@Path("")
class RestNodesHotThreadsResource extends RestBaseResource {

    @GET
    @Path("/_cluster/nodes/hotthreads")
    @Secure(Role.ADMIN)
    public void hotThreads(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_cluster/nodes/hot_threads")
    @Secure(Role.ADMIN)
    public void hot_threads(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_cluster/nodes/{nodeId}/hotthreads")
    @Secure(Role.ADMIN)
    public void hotThreadsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_cluster/nodes/{nodeId}/hot_threads")
    @Secure(Role.ADMIN)
    public void hot_threadsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/hotthreads")
    @Secure(Role.ADMIN)
    public void nodesHotThreads(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/hot_threads")
    @Secure(Role.ADMIN)
    public void nodesHot_threads(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/hotthreads")
    @Secure(Role.ADMIN)
    public void nodesHotThreadsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/hot_threads")
    @Secure(Role.ADMIN)
    public void nodesHot_threadsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
