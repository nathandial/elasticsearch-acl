package com.searchly

import com.searchly.client.Client
import com.searchly.client.elasticsearch.ElasticSearchClient
import com.searchly.node.SearchlyNode
import com.searchly.repository.facade.AccountFacade
import com.searchly.repository.facade.hazelcast.HazelCastAccountFacade
import com.searchly.repository.store.AccountStore
import com.searchly.repository.store.elasticsearch.AccountElasticSearchStore
import com.searchly.rest.filters.AuthenticateFilter
import com.searchly.rest.filters.JsonRequestBodyParserFilter
import com.searchly.rest.resources.account.RestAccountRegisterResource
import com.searchly.rest.resources.base.RestBaseResource
import com.searchly.rest.resources.cluster.health.RestHealthResource
import com.searchly.rest.resources.cluster.node.hotthreads.RestNodesHotThreadsResource
import com.searchly.rest.resources.cluster.node.info.RestNodesInfoResource
import com.searchly.rest.resources.cluster.node.stats.RestNodesStatsResource
import com.searchly.rest.resources.cluster.reroute.RestClusterRerouteResource
import com.searchly.rest.resources.cluster.settings.RestClusterSettingsResource
import com.searchly.rest.resources.cluster.shards.RestClusterSearchShardsResource
import com.searchly.rest.resources.cluster.state.RestClusterStateResource
import com.searchly.rest.resources.common.RestCommonResource
import com.searchly.rest.resources.core.bulk.RestBulkResource
import com.searchly.rest.resources.core.count.RestCountResource
import com.searchly.rest.resources.core.deletebyquery.RestDeleteByQueryResource
import com.searchly.rest.resources.core.explain.RestExplainResource
import com.searchly.rest.resources.core.get.RestGetResource
import com.searchly.rest.resources.core.index.RestIndexResource
import com.searchly.rest.resources.core.mlt.RestMoreLikeThisResource
import com.searchly.rest.resources.core.percolate.RestPercolateResource
import com.searchly.rest.resources.core.search.RestSearchResource
import com.searchly.rest.resources.core.suggest.RestSuggestResource
import com.searchly.rest.resources.core.update.RestUpdateResource
import com.searchly.rest.resources.indices.alias.RestAliasResource
import com.searchly.rest.resources.indices.analyze.RestAnalyzeResource
import com.searchly.rest.resources.indices.cache.clear.RestClearIndicesCacheResource
import com.searchly.rest.resources.indices.close.RestCloseIndexResource
import com.searchly.rest.resources.indices.create.RestCreateIndexResource
import com.searchly.rest.resources.indices.delete.RestDeleteIndexResource
import com.searchly.rest.resources.indices.exists.RestExistsResource
import com.searchly.rest.resources.indices.flush.RestFlushResource
import com.searchly.rest.resources.indices.mapping.RestMappingResource
import com.searchly.rest.resources.indices.open.RestOpenIndexResource
import com.searchly.rest.resources.indices.optimize.RestOptimizeResource
import com.searchly.rest.resources.indices.refresh.RestRefreshResource
import com.searchly.rest.resources.indices.segments.RestIndicesSegmentsResource
import com.searchly.rest.resources.indices.settings.RestSettingsResource
import com.searchly.rest.resources.indices.stats.RestIndicesStatsResource
import com.searchly.rest.resources.indices.status.RestIndicesStatusResource
import com.searchly.rest.resources.indices.validate.RestValidateQueryResource
import org.elasticsearch.common.inject.AbstractModule
import org.elasticsearch.common.inject.multibindings.Multibinder
import org.elasticsearch.common.settings.Settings

/**
 @author ferhat
 */
class AclModule extends AbstractModule {

    def settings

    public AclModule(Settings settings) {
        this.settings = settings
    }

    @Override
    protected void configure() {

        // node
        bind(SearchlyNode.class).asEagerSingleton()

        // client
        bind(Client.class).to(ElasticSearchClient.class).asEagerSingleton()

        // facade
        bind(AccountFacade.class).to(HazelCastAccountFacade.class).asEagerSingleton()

        // store
        bind(AccountStore.class).to(AccountElasticSearchStore.class).asEagerSingleton()

        // interceptors
        bind(AuthenticateFilter.class).asEagerSingleton()
        bind(JsonRequestBodyParserFilter.class).asEagerSingleton()

        // rest resources
        def actionBinder = Multibinder.newSetBinder(binder(), RestBaseResource.class)
        actionBinder.with {

            // core
            addBinding().to(RestCommonResource.class).asEagerSingleton()
            addBinding().to(RestBulkResource.class).asEagerSingleton()
            addBinding().to(RestCountResource.class).asEagerSingleton()
            addBinding().to(RestDeleteIndexResource.class).asEagerSingleton()
            addBinding().to(RestDeleteByQueryResource.class).asEagerSingleton()
            addBinding().to(RestExplainResource.class).asEagerSingleton()
            addBinding().to(RestGetResource.class).asEagerSingleton()
            addBinding().to(RestIndexResource.class).asEagerSingleton()
            addBinding().to(RestMoreLikeThisResource.class).asEagerSingleton()
            addBinding().to(RestPercolateResource.class).asEagerSingleton()
            addBinding().to(RestSearchResource.class).asEagerSingleton()
            addBinding().to(RestSuggestResource.class).asEagerSingleton()
            addBinding().to(RestUpdateResource.class).asEagerSingleton()

            // indices
            addBinding().to(RestCreateIndexResource.class).asEagerSingleton()
            addBinding().to(RestDeleteIndexResource.class).asEagerSingleton()
            addBinding().to(RestAliasResource.class).asEagerSingleton()
            addBinding().to(RestAnalyzeResource.class).asEagerSingleton()
            addBinding().to(RestClearIndicesCacheResource.class).asEagerSingleton()
            addBinding().to(RestCloseIndexResource.class).asEagerSingleton()
            addBinding().to(RestExistsResource.class).asEagerSingleton()
            addBinding().to(RestFlushResource.class).asEagerSingleton()
            addBinding().to(RestMappingResource.class).asEagerSingleton()
            addBinding().to(RestOpenIndexResource.class).asEagerSingleton()
            addBinding().to(RestOptimizeResource.class).asEagerSingleton()
            addBinding().to(RestRefreshResource.class).asEagerSingleton()
            addBinding().to(RestIndicesSegmentsResource.class).asEagerSingleton()
            addBinding().to(RestSettingsResource.class).asEagerSingleton()
            addBinding().to(RestIndicesStatsResource.class).asEagerSingleton()
            addBinding().to(RestIndicesStatusResource.class).asEagerSingleton()
            addBinding().to(RestValidateQueryResource.class).asEagerSingleton()

            // admin resources
            addBinding().to(RestHealthResource.class).asEagerSingleton()
            addBinding().to(RestNodesInfoResource.class).asEagerSingleton()
            addBinding().to(RestNodesHotThreadsResource.class).asEagerSingleton()
            addBinding().to(RestNodesStatsResource.class).asEagerSingleton()
            addBinding().to(RestClusterRerouteResource.class).asEagerSingleton()
            addBinding().to(RestClusterSettingsResource.class).asEagerSingleton()
            addBinding().to(RestClusterSearchShardsResource.class).asEagerSingleton()
            addBinding().to(RestClusterStateResource.class).asEagerSingleton()

            // account
            addBinding().to(RestAccountRegisterResource.class).asEagerSingleton()
        }
    }
}
