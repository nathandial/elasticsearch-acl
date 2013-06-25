package com.searchly.rest.resources.core.index

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
 * @author ferhat
 */
@Path("/")
class RestIndexResource extends RestBaseResource {

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}")
    public void index(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeIndexRequest(request, response, index)
    }

    @Secure(Role.MEMBER)
    @PUT
    @Path("{index}/{type}/{id}")
    public void putIndexWithId(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeIndexRequest(request, response, index)
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}/{id}")
    public void postIndexWithId(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeIndexRequest(request, response, index)
    }

    @Secure(Role.MEMBER)
    @PUT
    @Path("{index}/{type}/{id}/_create")
    public void putIndexWithIdAndCreate(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeIndexRequest(request, response, index)
    }

    @Secure(Role.MEMBER)
    @POST
    @Path("{index}/{type}/{id}/_create")
    public void postIndexWithIdCreate(@PathParam("index") final String index, @Suspended final AsyncResponse response, @Context final HttpRequest request) {
        executeIndexRequest(request, response, index)
    }

    def executeIndexRequest(request, response, index) {
        def account = account(request)
        def body = request.getAttribute(PARSED_JSON_BODY)
        def bodySize = body?.toString()?.length()
        if (isAuthorized(account, index)) {
            if ((account.doc_count ?: (account.doc_count = 0)) + 1 > account?.plan?.max_docs_count) {
                throw new NotAcceptableException(new ServerResponse('{"message":"you have reached maximum document count for your current plan"}', Response.Status.NOT_ACCEPTABLE.statusCode, new Headers<Object>()))
            } else if ((account.doc_size ?: (account.doc_size = 0)) + bodySize > account?.plan?.max_docs_size) {
                throw new NotAcceptableException(new ServerResponse('{"message":"you have reached maximum document count for your current plan"}', Response.Status.NOT_ACCEPTABLE.statusCode, new Headers<Object>()))
            }
            account?.doc_count += 1
            account?.doc_size += bodySize
            accountFacade.putAccount(account)

            def callback = [fn: { restResponse, options ->
                if (restResponse.status != 201) {
                    options.account?.doc_count -= 1
                    account?.doc_size -= bodySize
                    accountFacade.putAccount(options.account)
                } else {
                    // check if this is an update

                }
            }, doc_size: bodySize, account: account]

            execute(request, response, index, account, null, callback)
        }
    }
}
