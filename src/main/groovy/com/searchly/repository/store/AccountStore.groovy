package com.searchly.repository.store

/**
 @author ferhat
 */
public interface AccountStore {
    def get(apiKey)

    def save(account)

    def delete(apiKey)

    def init()

    def isAccountExists(account)

    def initialized()
}