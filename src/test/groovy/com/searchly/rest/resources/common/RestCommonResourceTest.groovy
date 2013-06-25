package com.searchly.rest.resources.common

import junit.framework.Assert
import org.jboss.resteasy.mock.MockHttpRequest
import org.jboss.resteasy.mock.MockHttpResponse
import org.jboss.resteasy.test.BaseResourceTest
import org.junit.Test

/**
 @author ferhat
 */
class RestCommonResourceTest extends BaseResourceTest {
    @Test
    public void base() {
        addPerRequestResource(RestCommonResource.class)
        def request = MockHttpRequest.get("/")
        def response = new MockHttpResponse()
        dispatcher.invoke(request, response)
        Assert.assertEquals('{"ok": true,"status": 200,"name": "searchly","version": {"number": "0.90.1","snapshot_build": false}}', response.getContentAsString())
    }
}
