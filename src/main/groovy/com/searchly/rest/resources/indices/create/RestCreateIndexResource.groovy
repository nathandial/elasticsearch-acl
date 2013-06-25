package com.searchly.rest.resources.indices.create

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import org.jboss.resteasy.core.Headers
import org.jboss.resteasy.core.ServerResponse
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response

/**
 @author ferhat
 */
@Path("/{index}")
class RestCreateIndexResource extends RestBaseResource {

    def static banned = [
            'refresh_interval', 'term_index_interval', 'term_index_divisor', 'compound_format',
            'translog', 'cache', 'gateway', 'routing', 'recovery', 'gc_deletes', 'ttl', 'store', 'refresh'
    ]

    @POST
    @Secure(Role.MEMBER)
    public void createWithPost(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeRequest(index, request, response)
    }

    @PUT
    @Secure(Role.MEMBER)
    public void createWithPut(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeRequest(index, request, response)
    }

    def executeRequest(index, request, response) {
        def account = account(request)
        def safeBody = buildSafeIndexCreateRequest(request.getAttribute(PARSED_JSON_BODY), account)
        // plan check
        if (account?.indices?.size() >= account?.plan?.max_index_count) {
            throw new BadRequestException(new ServerResponse('{"message":"you have created maximum indices for your current plan"}', Response.Status.BAD_REQUEST.statusCode, new Headers<Object>()))
        }
        if (account?.indices) {
            // index already exists
            if (account.indices."$index") throw new BadRequestException(new ServerResponse('{"message":"index already exits"}',
                    Response.Status.BAD_REQUEST.statusCode, new Headers<Object>()))
            account.indices << [("$index" as String): ("${account.email.replace('@', '_at_')}_${index}" as String)]
        } else {
            account.indices = [("$index" as String): ("${account.email.replace('@', '_at_')}_${index}" as String)]
        }
        accountFacade.putAccount(account)

        def callback = [fn: { restResponse, options ->
            if (restResponse.status != 200) {
                // index creation failed at search backend
                options.account.indices.remove(options.index)
                accountFacade.putAccount(options.account)
            }
        }, index: index, account: account]

        execute(request, response, index, account, safeBody, callback)
    }

    static def buildSafeIndexCreateRequest(bodyMap, account) {
        try {
            def settings = bodyMap?.settings?.index ?: bodyMap?.settings
            def safeRequestBody
            if (settings) {
                safeRequestBody = settings.findAll { !banned.contains(it.key) }
                if (account?.plan?.number_of_replicas)
                    safeRequestBody << ["number_of_replicas": account.plan.number_of_replicas]
                if (account?.plan?.number_of_shards)
                    safeRequestBody << ["number_of_shards": account.plan.number_of_shards]
                if (account?.plan?.node_tag)
                    safeRequestBody << ["index.routing.allocation.include.tag": account.plan.node_tag]
                bodyMap.putAll(['settings': safeRequestBody])
                return bodyMap
            } else {
                safeRequestBody = [:]
                if (account?.plan?.number_of_replicas)
                    safeRequestBody << ["number_of_replicas": account.plan.number_of_replicas]
                if (account?.plan?.number_of_shards)
                    safeRequestBody << ["number_of_shards": account.plan.number_of_shards]
                if (account?.plan?.node_tag)
                    safeRequestBody << ["index.routing.allocation.include.tag": account.plan.node_tag]
                if (bodyMap) {
                    bodyMap.putAll(['settings': safeRequestBody])
                    return bodyMap
                }
                return ['settings': safeRequestBody]
            }
        } catch (ignored) {
            throw new BadRequestException('{"message":"index create request is invalid"}')
        }
    }
}