package com.searchly.repository.facade.hazelcast

import com.hazelcast.config.Config
import com.hazelcast.config.GroupConfig
import com.hazelcast.config.MapConfig
import com.hazelcast.config.NearCacheConfig
import com.hazelcast.core.Hazelcast
import com.searchly.repository.facade.AccountFacade
import com.searchly.repository.store.AccountStore
import org.elasticsearch.ElasticSearchException
import org.elasticsearch.common.component.AbstractLifecycleComponent
import org.elasticsearch.common.inject.Inject
import org.elasticsearch.common.settings.Settings

/**
 @author ferhat
 */

class HazelCastAccountFacade extends AbstractLifecycleComponent<HazelCastAccountFacade> implements AccountFacade {

    def hazelcastInstance
    def accountMap
    def accountStore

    @Inject
    HazelCastAccountFacade(AccountStore accountStore, Settings settings) {
        super(settings)
        this.accountStore = accountStore
    }

    @Override
    def getAccount(apiKey) {
        def account = accountMap.get(apiKey)
        if (!account)
            account = accountStore.get(apiKey)
        return account
    }

    @Override
    def putAccount(account) {
        accountMap.put(account.api_key, account)
        accountStore.save(account)
    }

    @Override
    def getAllAccounts() {
        return null
    }

    @Override
    def removeAccount(apiKey) {
        accountMap.remove(apiKey)
        accountStore.delete(apiKey)
    }

    @Override
    def isAccountExists(email) {
        return accountStore.isAccountExists(email)
    }

    @Override
    protected void doStart() throws ElasticSearchException {
        def config = new Config()
        def network = config.getNetworkConfig()
        network.with {
            setPort(5701)
            setPortAutoIncrement(true)
            getInterfaces().setEnabled(false)
        }

        def join = network.getJoin()

        /*
        if (false) {
            join.getMulticastConfig().setEnabled(false)
            join.getTcpIpConfig().setEnabled(false)
            join.getAwsConfig().with {
                setEnabled(true)
                if ("cloud.aws.access_key")
                    setAccessKey(settings.get("cloud.aws.access_key"))
                if (settings.get("cloud.aws.secret_key"))
                    setSecretKey(settings.get("cloud.aws.secret_key"))
                if (settings.get("cloud.aws.region"))
                    setRegion(settings.get("cloud.aws.region"))
                if (settings.get("discovery.ec2.groups"))
                    setSecurityGroupName(settings.get("discovery.ec2.groups"))
            }
            log.info("HazelCast EC2 configuration: {}", join.getAwsConfig().toString())
        }
        */

        // create accounts map
        def accountMapNearCacheConfig = new NearCacheConfig()
        def accountMapConfig = new MapConfig()
        accountMapConfig.with {
            setName("account")
            setBackupCount(0)
            setAsyncBackupCount(1)
            setNearCacheConfig(accountMapNearCacheConfig)
        }

        // create group
        def groupConfig = new GroupConfig()
        groupConfig.with {
            setName("test")
            setPassword("ec2password123456es")
        }

        config.with {
            addMapConfig(accountMapConfig)
            setInstanceName("test")
            setGroupConfig(groupConfig)
        }

        hazelcastInstance = Hazelcast.newHazelcastInstance(config)
        logger.info("HazelCast configuration: {}", hazelcastInstance.getConfig().toString())
        accountMap = hazelcastInstance.getMap("account")
    }

    @Override
    protected void doStop() throws ElasticSearchException {
        if (hazelcastInstance)
            hazelcastInstance.stop()
    }

    @Override
    protected void doClose() throws ElasticSearchException {
        if (hazelcastInstance)
            hazelcastInstance.stop()
    }
}