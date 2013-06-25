package com.searchly.rest.resources.indices.create

import com.searchly.rest.resources.base.TestBase
import org.junit.Assert
import org.junit.Test

import javax.ws.rs.BadRequestException

/**
 @author ferhat
 */
@Mixin(TestBase)
class RestCreateIndexResourceTest {

    @Test
    public void createIndexWithPost() {
        def map = executeAsync(RestCreateIndexResource,
                { request ->
                    Assert.assertEquals(['method': 'POST',
                            'path': 'test_at_abcdefg.com_rogue',
                            'encodedPath': '/test_at_abcdefg.com_rogue',
                            'content': '{"settings":{"number_of_shards":1,"number_of_replicas":1,"index.routing.allocation.include.tag":"level0"}}'], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 1,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ], indices: [rogue: "test_at_abcdefg.com_rogue"]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            return account
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'post',
                ["settings": ["index": ["number_of_shards": 3, "number_of_replicas": 2, "refresh_interval": -1]]])

        map.mock.use {
            map.resource.createWithPost("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void createIndexWithPut() {
        def map = executeAsync(RestCreateIndexResource,
                { request ->
                    Assert.assertEquals(['method': 'PUT',
                            'path': 'test_at_abcdefg.com_warrior',
                            'encodedPath': '/test_at_abcdefg.com_warrior',
                            'content': '{"settings":{"number_of_shards":1,"number_of_replicas":1,"index.routing.allocation.include.tag":"level0"}}'], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 1,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ], indices: [warrior: "test_at_abcdefg.com_warrior"]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            return account
                        }
                ])

        def request = mockRequest('/warrior', "3d0ea926323a29bfd6e91476a5012345", 'put',
                ["settings": ["index": ["number_of_shards": 3, "number_of_replicas": 2, "refresh_interval": -1]]])

        map.mock.use {
            map.resource.createWithPut("warrior", mockAsyncResponse(), request)
        }
    }

    @Test
    public void createIndexWithPutNoBody() {
        def map = executeAsync(RestCreateIndexResource,
                { request ->
                    Assert.assertEquals(
                            ['method': 'PUT',
                                    'path': 'test_at_abcdefg.com_rogue',
                                    'encodedPath': '/test_at_abcdefg.com_rogue',
                                    'content': '{"settings":{"number_of_replicas":1,"number_of_shards":1,"index.routing.allocation.include.tag":"level0"}}'], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([indices: [warrior: "test_at_abcdefg.com_warrior", rogue: "test_at_abcdefg.com_rogue"],
                                    role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 2,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            result.plan.put("max_index_count", 2)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'put')
        map.mock.use {
            map.resource.createWithPut("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void createIndexWithPostNoBody() {
        def map = executeAsync(RestCreateIndexResource,
                { request ->
                    Assert.assertEquals(
                            ['method': 'POST',
                                    'content': '{"settings":{"number_of_replicas":1,"number_of_shards":1,"index.routing.allocation.include.tag":"level0"}}',
                                    'path': 'test_at_abcdefg.com_rogue',
                                    'encodedPath': '/test_at_abcdefg.com_rogue'], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([indices: [warrior: "test_at_abcdefg.com_warrior", rogue: "test_at_abcdefg.com_rogue"],
                                    role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 2,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            result.plan.put("max_index_count", 2)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'post')
        map.mock.use {
            map.resource.createWithPost("rogue", mockAsyncResponse(), request)
        }
    }

    @Test
    public void createPostIndexWithLimitationError() {
        def map = executeAsync(RestCreateIndexResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'post')
        map.mock.use {
            try {
                map.resource.createWithPost("rogue", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof BadRequestException)
                Assert.assertEquals('{"message":"you have created maximum indices for your current plan"}', e.response.entity)
            }
        }
    }

    @Test
    public void createPutIndexWithLimitationError() {
        def map = executeAsync(RestCreateIndexResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            return result
                        }
                ])

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'put')
        map.mock.use {
            try {
                map.resource.createWithPut("rogue", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof BadRequestException)
                Assert.assertEquals('{"message":"you have created maximum indices for your current plan"}', e.response.entity)
            }
        }
    }

    @Test
    public void indexCreateWithPostExisting() {
        def map = executeAsync(RestCreateIndexResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            result.plan.put("max_index_count", 2)
                            return result
                        }
                ])

        def request = mockRequest('/warrior', "3d0ea926323a29bfd6e91476a5012345", 'post')
        map.mock.use {
            try {
                map.resource.createWithPost("warrior", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof BadRequestException)
                Assert.assertEquals('{"message":"index already exits"}', e.response.entity)
            }
        }
    }

    @Test
    public void indexCreateWithPutExisting() {
        def map = executeAsync(RestCreateIndexResource, null,
                [
                        getAccount: { apiKey ->
                            def result = [indices: [warrior: "test_at_abcdefg.com_warrior"]]
                            result.putAll(account)
                            result.plan.put("max_index_count", 2)
                            return result
                        }
                ])

        def request = mockRequest('/warrior', "3d0ea926323a29bfd6e91476a5012345", 'put')
        map.mock.use {
            try {
                map.resource.createWithPut("warrior", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof BadRequestException)
                Assert.assertEquals('{"message":"index already exits"}', e.response.entity)
            }
        }
    }

    @Test
    public void invalidIndexCreateWithPost() {
        def map = executeAsync(RestCreateIndexResource)
        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'post', ['settings': 'shard2'])
        map.mock.use {
            try {
                map.resource.createWithPost("rogue", mockAsyncResponse(), request)
            } catch (e) {
                Assert.assertTrue(e instanceof BadRequestException)
            }
        }
    }

    // These tests assumes that they will fail at ES side
    @Test
    public void createIndexWithPostWithInvalidBody() {
        def map = executeAsync(RestCreateIndexResource,
                { request ->
                    Assert.assertEquals(['method': 'POST',
                            'content': '{"mappings":{"type1":{"properties":{"field1":{"type":"string","index":"rogue"}}}},' +
                                    '"settings":{"number_of_replicas":1,"number_of_shards":1,"index.routing.allocation.include.tag":"level0"}}',
                            'path': 'test_at_abcdefg.com_rogue',
                            'encodedPath': '/test_at_abcdefg.com_rogue'
                    ], request)
                },
                [
                        putAccount: { account ->
                            Assert.assertEquals('3d0ea926323a29bfd6e91476a5012345', account.api_key)
                            Assert.assertEquals([role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                                    plan: [
                                            "number_of_shards": 1,
                                            "number_of_replicas": 1,
                                            "max_index_count": 1,
                                            "max_docs_count": 10000,
                                            "max_docs_size": 104857600,
                                            "request_per_second": 10,
                                            "node_tag": "level0"
                                    ], indices: [rogue: "test_at_abcdefg.com_rogue"]].toString(), account.toString())
                        },
                        getAccount: { apiKey ->
                            return account
                        }
                ],
                { options ->
                    // testing callback here, how can we test fn: clojure?
                    Assert.assertEquals("rogue", options.index)
                    Assert.assertEquals([role: "member", email: "test@abcdefg.com", api_key: "3d0ea926323a29bfd6e91476a5012345",
                            plan: [
                                    "number_of_shards": 1,
                                    "number_of_replicas": 1,
                                    "max_index_count": 1,
                                    "max_docs_count": 10000,
                                    "max_docs_size": 104857600,
                                    "request_per_second": 10,
                                    "node_tag": "level0"
                            ], indices: [rogue: "test_at_abcdefg.com_rogue"]], options.account)
                })

        def request = mockRequest('/rogue', "3d0ea926323a29bfd6e91476a5012345", 'post',
                ["mappings": ["type1": ["properties": ["field1": ["type": "string", "index": "rogue"]]]]])

        map.mock.use {
            map.resource.createWithPost("rogue", mockAsyncResponse(), request)
        }
    }
}