package com.searchly.client

/**
 * @author ferhat
 */
interface Client {
    def executeAsync(request, response, callback)
}
