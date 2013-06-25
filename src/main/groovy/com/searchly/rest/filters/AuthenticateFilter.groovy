package com.searchly.rest.filters

import com.searchly.repository.facade.AccountFacade
import com.searchly.repository.store.AccountStore
import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.security.Role
import com.searchly.rest.security.Secure
import groovy.util.logging.Slf4j
import org.elasticsearch.common.inject.Inject
import org.jboss.resteasy.core.Headers
import org.jboss.resteasy.core.ServerResponse
import sun.misc.BASE64Decoder

import javax.ws.rs.ForbiddenException
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

/**
 @author ferhat
 */

@Slf4j
class AuthenticateFilter implements ContainerRequestFilter {

    @Inject
    def AccountFacade accountFacade

    @Inject
    def AccountStore accountStore


    static def authenticateResponse() {
        def headers = new Headers<Object>()
        headers.add(HttpHeaders.WWW_AUTHENTICATE, 'Basic realm="credentials"')
        return new ServerResponse('{"message":"Given api key is invalid!"}', Response.Status.UNAUTHORIZED.statusCode, headers)
    }

    @Override
    void filter(ContainerRequestContext containerRequestContext) throws IOException {

        if (!accountStore.initialized()) {
            accountStore.init()
        }

        def secure = containerRequestContext.getResourceMethod().getMethod().getAnnotation(Secure.class)
        if (secure) {
            // check http basic
            def httpBasic = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
            if (httpBasic) {
                try {
                    def dec = new BASE64Decoder()
                    // BASIC ...... we need encoded part
                    def basicDecoded = new String(dec.decodeBuffer(httpBasic.substring(6)))
                    // api-key:abc1231321
                    def apiKey = basicDecoded.split(":")[1]
                    def account = accountFacade.getAccount(apiKey)
                    if (account) {
                        // check role
                        if (Role.valueOf((account.role?.toString()?.toUpperCase()) ?: "MEMBER").can(secure.value())) {
                            containerRequestContext.setProperty(RestBaseResource.API_KEY, apiKey)
                        } else {
                            throw new ForbiddenException(new ServerResponse('{"message":"You do not have enough rights to access this resource"}', Response.Status.FORBIDDEN.statusCode, new Headers<Object>()))
                        }
                    } else {
                        throw new NotAuthorizedException(authenticateResponse())
                    }
                } catch (e) {
                    log.error("Can not parse http header", e)
                    throw new NotAuthorizedException(authenticateResponse())
                }
            } else {
                throw new NotAuthorizedException(authenticateResponse())
            }
        }
    }
}
