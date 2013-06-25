package com.searchly.client.elasticsearch

import com.searchly.client.Client
import org.elasticsearch.common.inject.Inject
import org.elasticsearch.rest.RestController

/**
 * @author ferhat
 */
class ElasticSearchClient implements Client {

    def restController

    @Inject
    def ElasticSearchClient(RestController restController) {
        this.restController = restController
    }

    @Override
    def executeAsync(requestHash, response, callback) {
        def restRequest = new RestEasyRestRequest(requestHash)
        def restChannel = new RestEasyRestChannel(response, callback)
        restController.executeHandler(restRequest, restChannel)
    }
}
