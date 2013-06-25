package com.searchly.rest.resources.cluster.shards

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 @author ferhat
 */
@Path("/")
class RestClusterSearchShardsResource extends RestBaseResource {
    @GET
    @Path("_search_shards")
    @Secure(Role.ADMIN)
    public void getSearchShards(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @POST
    @Path("_search_shards")
    @Secure(Role.ADMIN)
    public void postSearchShards(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @POST
    @Path("{index}/_search_shards")
    @Secure(Role.ADMIN)
    public void postSearchShardsWithIndex(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("{index}/_search_shards")
    @Secure(Role.ADMIN)
    public void getSearchShardsWithIndex(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @GET
    @Path("{index}/{type}/_search_shards")
    @Secure(Role.ADMIN)
    public void getSearchShardsWithIndexAndType(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }

    @POST
    @Path("{index}/{type}/_search_shards")
    @Secure(Role.ADMIN)
    public void postSearchShardsWithIndexAndType(@Suspended final AsyncResponse response, @Context final HttpRequest request) {
        execute(request, response)
    }
}
