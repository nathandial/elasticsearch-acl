package com.searchly.rest.resources.base

import com.searchly.client.Client
import com.searchly.repository.facade.AccountFacade
import groovy.mock.interceptor.MockFor
import org.jboss.resteasy.mock.MockHttpRequest

import javax.ws.rs.container.AsyncResponse

/**
 @author ferhat
 */
class TestBase {

    def executeAsync = { clazz, requestCodeBlock = null, accountCodeBlock = null, requestCallback = null ->
        def clientMock = new MockFor(Client)
        clientMock.demand.executeAsync(0) { request, response, callback ->
            if (requestCodeBlock) requestCodeBlock(request)
            if (requestCallback) requestCallback(callback)
        }

        def accountFacadeMock = new MockFor(AccountFacade)
        accountFacadeMock.demand.getAccount(1) { apiKey ->
            if (accountCodeBlock) accountCodeBlock.getAccount(apiKey)
        }
        accountFacadeMock.demand.putAccount(1) { account ->
            if (accountCodeBlock) accountCodeBlock.putAccount(account)
        }

        def resource = clazz.newInstance()
        resource.client = clientMock.proxyDelegateInstance() as Client
        resource.accountFacade = accountFacadeMock.proxyDelegateInstance() as AccountFacade
        return [mock: clientMock, resource: resource]
    }

    def mockRequest(path, apiKey, method = 'get', body = null) {
        def request = MockHttpRequest."$method"(path)
        if (body)
            request.setAttribute(RestBaseResource.PARSED_JSON_BODY, body)
        request.setAttribute(RestBaseResource.API_KEY, apiKey)
        return request
    }

    def mockAsyncResponse(resume = null) {
        return [
                setTimeout: { time, unit -> true },
                resume: { obj ->
                    resume(obj)
                    return true
                }
        ] as AsyncResponse
    }

    def account = [
            role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
            plan: [
                    number_of_shards: 1,
                    number_of_replicas: 1,
                    max_index_count: 1,
                    max_docs_count: 10000,
                    max_docs_size: 104857600,
                    request_per_second: 10,
                    node_tag: "level0"
            ]
    ]
}
