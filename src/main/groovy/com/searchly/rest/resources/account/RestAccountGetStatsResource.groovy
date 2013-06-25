package com.searchly.rest.resources.account

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import groovy.json.JsonOutput

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

/**
 * @author ferhat
 */
@Path("/")
class RestAccountGetStatsResource extends RestBaseResource {
    @Secure(Role.SYSTEM)
    @GET
    @Path("_user/stats/{targetApiKey}")
    public String stats(@PathParam("targetApiKey") final String targetApiKey) {
        def account = accountFacade.getAccount(targetApiKey)
        return JsonOutput.toJson(account)
    }
}
