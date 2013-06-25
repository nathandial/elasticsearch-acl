package com.searchly.client.elasticsearch

import org.elasticsearch.common.bytes.BytesArray
import org.elasticsearch.common.bytes.BytesReference
import org.elasticsearch.rest.RestRequest
import org.elasticsearch.rest.support.AbstractRestRequest
import org.elasticsearch.rest.support.RestUtils

/**
 @author ferhat
 */
class RestEasyRestRequest extends AbstractRestRequest implements RestRequest {

    def requestHash
    def params = [:]
    def rawPath
    def content

    RestEasyRestRequest(requestHash) {
        this.requestHash = requestHash
        this.rawPath = requestHash.encodedPath
        this.content = new BytesArray(requestHash.content ?: "")
        def uri = requestHash.path
        int pathEndPos = uri.indexOf('?')
        if (pathEndPos > 0) {
            RestUtils.decodeQueryString(uri, pathEndPos + 1, params)
        }
    }

    @Override
    RestRequest.Method method() {
        RestRequest.Method.valueOf(requestHash.method)
    }

    @Override
    String uri() {
        requestHash.path
    }

    @Override
    String rawPath() {
        return rawPath
    }

    @Override
    boolean hasContent() {
        return content.length() > 0
    }

    @Override
    boolean contentUnsafe() {
        return false
    }

    @Override
    BytesReference content() {
        return content
    }

    @Override
    String header(String s) {
        return null
    }

    @Override
    boolean hasParam(String key) {
        return params.containsKey(key)
    }

    @Override
    String param(String key) {
        return params.get(key)
    }

    @Override
    String param(String key, String defaultValue) {
        def value = params.get(key)
        if (!value) {
            return defaultValue
        }
        return value
    }

    @Override
    Map<String, String> params() {
        return params
    }
}
