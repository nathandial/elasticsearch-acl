package com.searchly.rest.resources.indices.exists

import com.searchly.rest.resources.base.TestBase
import org.junit.Assert
import org.junit.Test

import javax.ws.rs.ForbiddenException

/**
 * @author ferhat
 */
@Mixin(TestBase)
class RestExistsResourceTest {

    @Test
    public void indexExists() {
        def map = executeAsync(RestExistsResource,
                { request ->
                    Assert.assertEquals(['method': 'HEAD',
                            'path': 'test_at_abcdefg.com_rogue',
                            'encodedPath': '/test_at_abcdefg.com_rogue',
                            'content': null], request)
                },
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'head')

        map.mock.use {
            map.resource.indexExists("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void typeExists() {
        def map = executeAsync(RestExistsResource,
                { request ->
                    Assert.assertEquals(['method': 'HEAD',
                            'path': 'test_at_abcdefg.com_rogue/type',
                            'encodedPath': '/test_at_abcdefg.com_rogue/type',
                            'content': null], request)
                },
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue/type', "3d0ea926323a29bfd6e91476a5012345", 'head')

        map.mock.use {
            map.resource.typeExists("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void unauthorizedIndexExists() {
        def map = executeAsync(RestExistsResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/warrior', "3d0ea926323a29bfd6e91476a5012345", 'head')

        map.mock.use {
            try {
                map.resource.indexExists("warrior", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof ForbiddenException)
                Assert.assertEquals('{"message":"you do not have enough rights to given index"}', e.response.entity)
            }
        }
    }

    @Test
    public void unauthorizedTypeExists() {
        def map = executeAsync(RestExistsResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/warrior/type', "3d0ea926323a29bfd6e91476a5012345", 'head')

        map.mock.use {
            try {
                map.resource.typeExists("warrior", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof ForbiddenException)
                Assert.assertEquals('{"message":"you do not have enough rights to given index"}', e.response.entity)
            }
        }
    }
}
