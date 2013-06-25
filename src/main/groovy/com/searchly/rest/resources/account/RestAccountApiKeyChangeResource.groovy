package com.searchly.rest.resources.account

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context

/**
 * @author ferhat
 */

@Path("/_user/api-key/change")
class RestAccountApiKeyChangeResource extends RestBaseResource {
    @POST
    @Secure(Role.SYSTEM)
    public String change(@Context final HttpRequest request) {
        def body = request.getAttribute(PARSED_JSON_BODY)
        def currentApiKey = body.current_api_key
        def newApiKey = body.new_api_key
        def account = accountFacade.getAccount(currentApiKey)
        accountFacade.removeAccount(account)
        account.api_key = newApiKey
        accountFacade.putAccount(account)
        return '{"message":"api key is changed"}'
    }
}
