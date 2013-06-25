package com.searchly.repository.store.elasticsearch

import com.searchly.repository.store.AccountStore
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.Client
import org.elasticsearch.common.inject.Inject
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.index.query.MatchQueryBuilder

/**
 @author ferhat
 */
@Slf4j
class AccountElasticSearchStore implements AccountStore {

    def Client client
    def INDEX = "users"
    def TYPE = "user"
    def state = "stop"


    @Inject
    AccountElasticSearchStore(Client client , Settings settings) {
        this.client = client
    }

    @Override
    def init() {
        def existsFuture = client.admin().indices().prepareExists(INDEX).execute()
        try {
            if (!existsFuture.actionGet().isExists()) {
                def indexSettings = ['index.auto_expand_replicas': '0-all', 'index.number_of_shards': 1, 'index.refresh_interval': '10s']

                def mapping = "{\n" +
                        "    \"user\": {\n" +
                        "        \"_all\": {\n" +
                        "            \"enabled\": false\n" +
                        "        },\n" +
                        "        \"properties\": {\n" +
                        "            \"api_key\": {\n" +
                        "                \"type\": \"string\",\n" +
                        "                \"index\": \"not_analyzed\"\n" +
                        "            },\n" +
                        "            \"plan\": {\n" +
                        "                \"properties\": {\n" +
                        "                    \"number_of_shards\": {\n" +
                        "                        \"type\": \"integer\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"number_of_replicas\": {\n" +
                        "                        \"type\": \"integer\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"max_index_count\": {\n" +
                        "                        \"type\": \"integer\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"max_docs_count\": {\n" +
                        "                        \"type\": \"long\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"max_docs_size\": {\n" +
                        "                        \"type\": \"long\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"request_per_second\": {\n" +
                        "                        \"type\": \"integer\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"node_tag\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"email\": {\n" +
                        "                \"type\": \"string\",\n" +
                        "                \"index\": \"not_analyzed\"\n" +
                        "            },\n" +
                        "            \"doc_count\": {\n" +
                        "                \"type\": \"long\",\n" +
                        "                \"index\": \"not_analyzed\"\n" +
                        "            },\n" +
                        "            \"doc_size\": {\n" +
                        "                \"type\": \"long\",\n" +
                        "                \"index\": \"not_analyzed\"\n" +
                        "            },\n" +
                        "            \"indices\": {\n" +
                        "                \"properties\": {\n" +
                        "                    \"native_name\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"name\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"aliases\": {\n" +
                        "                \"properties\": {\n" +
                        "                    \"native_name\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"index_native_name\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    },\n" +
                        "                    \"name\": {\n" +
                        "                        \"type\": \"string\",\n" +
                        "                        \"index\": \"not_analyzed\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"role\": {\n" +
                        "                \"type\": \"string\",\n" +
                        "                \"index\": \"not_analyzed\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"


                def created = client.admin().indices().prepareCreate(INDEX)
                        .setSettings(indexSettings).addMapping(TYPE, mapping).execute().get().isAcknowledged()
                if (created) {
                    def systemAccount = [api_key: 'system224353f9c729511ea6e509f048', role: 'system', request_per_second: 200, email: 'admin@searchly.com']
                    def future = client.prepareIndex().setIndex(INDEX)
                            .setType(TYPE).setId(systemAccount.get('api_key'))
                            .setRefresh(true).setSource(JsonOutput.toJson(systemAccount)).execute()
                    log.info("Created initial admin user with name {}", future.get().getId())
                }
            }
            state = "initiliazed"
        } catch (Exception e) {
            log.error("Can not initialize 'users' index", e)
        }
    }

    @Override
    def isAccountExists(account) {
        SearchResponse searchResponse = client.prepareSearch(INDEX).setTypes(TYPE)
                .setQuery(new MatchQueryBuilder('email', account.email)).execute().get()
        return searchResponse.getHits()?.getHits()?.size() > 0
    }

    @Override
    def get(apiKey) {
        try {
            GetResponse response = client.prepareGet().setIndex(INDEX).setType(TYPE).setId(apiKey).execute().get()
            return response.getSourceAsMap()
        } catch (Exception e) {
            log.error("Can not get account with api key {}", e, apiKey)
        }
    }

    @Override
    def save(account) {
        try {
            client.prepareIndex().setIndex(INDEX).setType(TYPE).setId(account['api_key'])
                    .setSource(account)
                    .setRefresh(false)
                    .execute(new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    log.debug("Account saved with apiKey {} and email {}", indexResponse.getId(), account['email'])
                }

                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Can not save user to users index with email {}", throwable, account['email'])
                }
            })
        } catch (Exception e) {
            log.error("Can not save user to users index with email {}", e, account['email'])
        }
    }

    @Override
    def delete(apiKey) {
        try {
            client.prepareDelete().setIndex(INDEX).setType(TYPE).setId(apiKey).execute()
        } catch (Exception e) {
            log.error("Can not delete account with api key {}", e, apiKey)
        }
    }

    def initialized(){
        return state.equals("initiliazed")
    }
}
