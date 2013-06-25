package com.searchly.rest.resources.account

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.core.Request

/**
 * @author ferhat
 */
@Slf4j
@Path("/")
class RestAccountDeleteResource extends RestBaseResource {
    @Secure(Role.SYSTEM)
    @GET
    @Path("_user/{targetApiKey}")
    public String delete(@PathParam("targetApiKey") final String targetApiKey) {
        def account = accountFacade.getAccount(targetApiKey)
        accountFacade.removeAccount(account)
        def indices = account?.indices?.collect { key, value -> value }?.join(",")
        if (indices)
            execute([uri: [requestUri: "/${indices}", encodedPath: "/${indices}"]] as Request,
                    [setTimeout: { time, unit -> true }, resume: { obj -> log.info(obj) }] as AsyncResponse)

        return '{"message":"account is deleted"}'
    }
}
