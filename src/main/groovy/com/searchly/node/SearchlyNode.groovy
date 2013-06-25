package com.searchly.node

import com.searchly.repository.store.AccountStore
import com.searchly.rest.filters.AuthenticateFilter
import com.searchly.rest.filters.JsonRequestBodyParserFilter
import com.searchly.rest.resources.base.RestBaseResource
import org.elasticsearch.ElasticSearchException
import org.elasticsearch.common.component.AbstractLifecycleComponent
import org.elasticsearch.common.inject.Inject
import org.elasticsearch.common.settings.Settings
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer
import org.jboss.resteasy.spi.ResteasyDeployment

/**
 @author ferhat
 */
class SearchlyNode extends AbstractLifecycleComponent<SearchlyNode> {

    def server
    def restResources
    def securityInterceptor
    def jsonRequestBodyParserFilter
    def AccountStore accountStore
    def Settings settings

    @Inject
    SearchlyNode(Settings settings, Set<RestBaseResource> restResources,
                 AuthenticateFilter securityInterceptor, JsonRequestBodyParserFilter jsonRequestBodyParserFilter,
                 AccountStore accountStore) {
        super(settings)
        this.settings = settings
        this.restResources = restResources
        this.securityInterceptor = securityInterceptor
        this.jsonRequestBodyParserFilter = jsonRequestBodyParserFilter
        this.accountStore = accountStore
    }

    @Override
    protected void doStart() throws ElasticSearchException {
        def server = new TJWSEmbeddedJaxrsServer()

        //def server = new NettyJaxrsServer()
        //server.setMaxRequestSize(1000)

        def deployment = new ResteasyDeployment()
        deployment.setResources(restResources.toList())
        server.setDeployment(deployment)

        int port = 8800
        server.setPort(port)
        server.start()

        server.getDeployment().getDispatcher().getProviderFactory().getContainerRequestFilterRegistry().registerSingleton(securityInterceptor)
        server.getDeployment().getDispatcher().getProviderFactory().getContainerRequestFilterRegistry().registerSingleton(jsonRequestBodyParserFilter)
    }

    @Override
    protected void doStop() throws ElasticSearchException {
        if (server)
            server.stop()
    }

    @Override
    protected void doClose() throws ElasticSearchException {
        if (server)
            server.stop()
    }
}
