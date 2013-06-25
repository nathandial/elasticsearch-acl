package com.searchly.rest.resources.base

import com.searchly.client.Client
import com.searchly.client.tenant.TenantRequest
import com.searchly.repository.facade.AccountFacade
import org.elasticsearch.common.inject.Inject
import org.jboss.resteasy.core.Headers
import org.jboss.resteasy.core.ServerResponse

import javax.ws.rs.ForbiddenException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.util.concurrent.TimeUnit

/**
 @author ferhat
 */
class RestBaseResource {

    public static final API_KEY = "apiKey"
    public static final PARSED_JSON_BODY = "jsonBodyAsMap"

    @Inject
    def Client client

    @Inject
    def AccountFacade accountFacade

    def account(request) {
        def apiKey = request.getAttribute(API_KEY)
        return accountFacade.getAccount(apiKey)
    }

    def execute(request, response, index = null, account = null, content = null, callback = null) {
        response.setTimeout(20, TimeUnit.SECONDS)
        def tenantRequest = new TenantRequest(account, request, index, content)
        client.executeAsync(tenantRequest.getRequest(), response, callback)
    }

    def isAuthorized(account, indices) {
        def result = false
        indices?.split(",")?.each { index ->
            if (!account?.indices?.get(index)) {
                result = false
                return
            } else {
                result = true
            }
        }
        if (!result) {
            throw new ForbiddenException(new ServerResponse('{"message":"you do not have enough rights to given index"}', Response.Status.FORBIDDEN.statusCode, new Headers<Object>()))
        } else {
            return true
        }
    }

    static def dummyOk() {
        return Response.ok('"{"ok":"true","_shards":{"total":"0","successful":"0","failed":"0"}}').status(200).type(MediaType.APPLICATION_JSON).build()
    }
}
