package com.searchly.rest.resources.cluster.node.info

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
class RestNodesInfoResource extends RestBaseResource {

    @GET
    @Path("/_cluster/nodes")
    @Secure(Role.ADMIN)
    public void clusterNodes(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_cluster/nodes/{nodeId}")
    @Secure(Role.ADMIN)
    public void clusterNodesWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes")
    @Secure(Role.ADMIN)
    public void nodes(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}")
    @Secure(Role.ADMIN)
    public void nodesWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/settings")
    @Secure(Role.ADMIN)
    public void nodesSettings(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/settings")
    @Secure(Role.ADMIN)
    public void nodesSettingsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/os")
    @Secure(Role.ADMIN)
    public void nodesOs(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/os")
    @Secure(Role.ADMIN)
    public void nodesOsWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/process")
    @Secure(Role.ADMIN)
    public void nodesProcess(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/process")
    @Secure(Role.ADMIN)
    public void nodesProcessWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/jvm")
    @Secure(Role.ADMIN)
    public void nodesJVM(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/jvm")
    @Secure(Role.ADMIN)
    public void nodesJVMWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/thread_pool")
    @Secure(Role.ADMIN)
    public void nodesThreadPool(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/thread_pool")
    @Secure(Role.ADMIN)
    public void nodesThreadPoolWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/network")
    @Secure(Role.ADMIN)
    public void nodesNetwork(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/network")
    @Secure(Role.ADMIN)
    public void nodesNetworkWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/transport")
    @Secure(Role.ADMIN)
    public void nodesTransport(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/transport")
    @Secure(Role.ADMIN)
    public void nodesTransportWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/http")
    @Secure(Role.ADMIN)
    public void nodesHttp(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("/_nodes/{nodeId}/http")
    @Secure(Role.ADMIN)
    public void nodesHttpWithId(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
