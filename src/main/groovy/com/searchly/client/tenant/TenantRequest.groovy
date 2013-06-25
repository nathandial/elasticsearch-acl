package com.searchly.client.tenant

import com.searchly.rest.resources.base.RestBaseResource
import groovy.json.JsonOutput

/**
 @author ferhat
 */
class TenantRequest {

    def requestHash

    // TODO make this configurable
    def replaceIndicesNames = true

    TenantRequest(account, request, index, content) {
        if (content) {
            content = JsonOutput.toJson(content) as String
        } else {
            def rawContent = request.getAttribute(RestBaseResource.PARSED_JSON_BODY)
            if (rawContent)
                content = JsonOutput.toJson(rawContent) as String
        }

        def path
        def encodedPath
        if (replaceIndicesNames && index) {
            def nativeName = account.indices.get(index)
            path = request.getUri().requestURI.toString().replace(index, nativeName)
            encodedPath = request.getUri().encodedPath.toString().replace(index, nativeName)
        } else {
            path = request.getUri().requestURI as String
            encodedPath = request.getUri().encodedPath as String
        }
        requestHash = [
                method: request.getHttpMethod() as String,
                content: content,
                path: path,
                encodedPath: encodedPath
        ]
    }

    def getRequest() {
        return requestHash
    }
}
