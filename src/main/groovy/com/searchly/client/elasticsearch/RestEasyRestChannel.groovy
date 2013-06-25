package com.searchly.client.elasticsearch

import org.elasticsearch.rest.RestChannel
import org.elasticsearch.rest.RestResponse

import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 @author ferhat
 */
class RestEasyRestChannel implements RestChannel {

    def byte[] content
    def int contentLength
    def int contentOffset
    def response
    def callback

    RestEasyRestChannel(response, callback) {
        this.response = response
        this.callback = callback
    }

    @Override
    void sendResponse(RestResponse restResponse) {
        content = restResponse.content()
        contentLength = restResponse.contentLength()
        contentOffset = restResponse.contentOffset()
        def jxResponse = Response.ok(new String(content, contentOffset, contentLength)).status(restResponse.status().status).type(MediaType.APPLICATION_JSON).build()
        response.resume(jxResponse)
        if (callback.fn)
            callback.fn(jxResponse, callback)
    }
}
