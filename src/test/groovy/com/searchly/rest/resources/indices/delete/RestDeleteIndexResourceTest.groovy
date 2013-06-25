package com.searchly.rest.resources.indices.delete

import com.searchly.rest.resources.base.TestBase
import org.junit.Assert
import org.junit.Test

import javax.ws.rs.ForbiddenException

/**
 * @author ferhat
 */
@Mixin(TestBase)
class RestDeleteIndexResourceTest {

    @Test
    public void deleteIndex() {
        def map = executeAsync(RestDeleteIndexResource,
                { request ->
                    Assert.assertEquals(['method': 'DELETE',
                            'path': 'test_at_abcdefg.com_rogue',
                            'encodedPath': '/test_at_abcdefg.com_rogue',
                            'content': null], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([
                                    indices: [:],
                                    role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 1,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'delete')

        map.mock.use {
            map.resource.deleteIndex("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void deleteUnauthorizedIndex() {
        def map = executeAsync(RestDeleteIndexResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [rogue: "test_at_abcdefg.com_rogue"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/warrior', "3d0ea926323a29bfd6e91476a5012345", 'delete')

        map.mock.use {
            try {
                map.resource.deleteIndex("warrior", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof ForbiddenException)
                Assert.assertEquals('{"message":"you do not have enough rights to given index"}', e.response.entity)
            }
        }
    }
}
