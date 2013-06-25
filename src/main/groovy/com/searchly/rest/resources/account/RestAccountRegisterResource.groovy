package com.searchly.rest.resources.account

import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import groovy.util.logging.Slf4j
import org.jboss.resteasy.core.Headers
import org.jboss.resteasy.core.ServerResponse
import org.jboss.resteasy.spi.HttpRequest

import javax.ws.rs.ForbiddenException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response

/**
 * @author ferhat
 */
@Slf4j
@Path("/_user/register")
class RestAccountRegisterResource extends RestBaseResource {

    def final emailPattern = /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[A-Za-z]{2,4}/

    @Secure(Role.SYSTEM)
    @POST
    public String register(@Context final HttpRequest request) {
        def account = request.getAttribute(PARSED_JSON_BODY)
        def accountApiKey = account?.api_key

        if (accountApiKey?.size() != 32) {
            log.error("Invalid api-key ${accountApiKey}, account creation failed")
            error('{"message":"invalid api-key account creation failed"}')
        }

        if (accountFacade.getAccount(accountApiKey)) {
            log.error("Api key ${accountApiKey} is already taken, account creation failed ")
            error('{"message":"api key is already taken, account creation failed"}')
        }

        if (!account?.email) {
            log.error("Email can not be blank, account creation failed ${account}")
            error('{"message":"email can not be blank, account creation failed"}')
        }

        if (!(account?.email ==~ emailPattern)) {
            log.error("Email ${account?.email} is invalid, account creation failed")
            error('{"message":"email is invalid, account creation failed"}')
        }

        if (accountFacade.isAccountExists(account)) {
            log.error("Email ${account?.email} is already taken, account creation failed")
            error('{"message":"email is already taken, account creation failed"}')
        }
        accountFacade.putAccount(account)
        return '{"message":"account created"}'
    }

    def error(message) {
        throw new ForbiddenException(new ServerResponse(message, Response.Status.FORBIDDEN.statusCode, new Headers<Object>()))
    }
}
