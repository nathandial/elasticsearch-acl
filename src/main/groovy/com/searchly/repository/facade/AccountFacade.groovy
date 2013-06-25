package com.searchly.repository.facade

/**
 @author ferhat
 */
interface AccountFacade {
    def getAccount(apiKey)

    def putAccount(account)

    def getAllAccounts()

    def removeAccount(apiKey)

    def isAccountExists(email)
}