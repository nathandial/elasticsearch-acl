package com.searchly.rest.resources.indices.flush

import com.searchly.rest.resources.base.TestBase
import org.junit.Assert
import org.junit.Test

import javax.ws.rs.ForbiddenException

/**
 * @author ferhat
 */
@Mixin(TestBase)
class RestFlushResourceTest {
    @Test
    public void postFlush() {
        def map = executeAsync(RestFlushResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue/_flush', "3d0ea926323a29bfd6e91476a5012345", 'post')

        def resume = { response ->
            Assert.assertEquals(200, response.status)
            Assert.assertEquals('"{"ok":"true","_shards":{"total":"0","successful":"0","failed":"0"}}', response.entity)
        }

        map.mock.use {
            map.resource.flushPost("rogue", mockAsyncResponse(resume), request)
        }
    }

    @Test
    public void postFlushInvalidIndex() {
        def map = executeAsync(RestFlushResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/warrior/_flush', "3d0ea926323a29bfd6e91476a5012345", 'post')

        try {
            map.resource.flushPost("warrior", mockAsyncResponse(), request)
        } catch (e) {
            Assert.assertTrue(e instanceof ForbiddenException)
            Assert.assertEquals('{"message":"you do not have enough rights to given index"}', e.response.entity)
        }
    }

    @Test
    public void getFlush() {
        def map = executeAsync(RestFlushResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue/_flush', "3d0ea926323a29bfd6e91476a5012345", 'get')

        def resume = { response ->
            Assert.assertEquals(200, response.status)
            Assert.assertEquals('"{"ok":"true","_shards":{"total":"0","successful":"0","failed":"0"}}', response.entity)
        }

        map.mock.use {
            map.resource.flushGet("rogue", mockAsyncResponse(resume), request)
        }
    }

    @Test
    public void getFlushInvalidIndex() {
        def map = executeAsync(RestFlushResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/warrior/_flush', "3d0ea926323a29bfd6e91476a5012345", 'get')

        try {
            map.resource.flushGet("warrior", mockAsyncResponse(), request)
        } catch (e) {
            Assert.assertTrue(e instanceof ForbiddenException)
            Assert.assertEquals('{"message":"you do not have enough rights to given index"}', e.response.entity)
        }
    }
}
