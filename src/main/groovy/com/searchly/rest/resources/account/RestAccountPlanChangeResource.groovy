package com.searchly.rest.resources.account

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context

/**
 * @author ferhat
 */
@Path("/_user/plan/change/{targetApiKey}")
class RestAccountPlanChangeResource extends RestBaseResource {
    @POST
    @Secure(Role.SYSTEM)
    public void change(@PathParam("targetApiKey") final String targetApiKey,
                       @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        def account = accountFacade.getAccount(targetApiKey)
        def updatedPlan = request.getAttribute(PARSED_JSON_BODY)
        account.plan.putAll(updatedPlan)
        accountFacade.putAccount(account)

        def callback = [
                fn: { restResponse, options ->
                    if (restResponse.status != 200) {
                        // update failed rollback
                        accountFacade.putAccount(options.account)
                    }
                }, account: account]

        execute(request, response, null, null, null, callback)
    }
}
