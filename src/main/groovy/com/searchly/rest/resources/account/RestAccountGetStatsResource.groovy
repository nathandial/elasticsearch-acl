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
@Path("/_user/stats/{targetApiKey}")
class RestAccountGetStatsResource extends RestBaseResource {
    @GET
    @Secure(Role.SYSTEM)
    public String stats(@PathParam("targetApiKey") final String targetApiKey) {
        def account = accountFacade.getAccount(targetApiKey)
        return JsonOutput.toJson(account)
    }
}
