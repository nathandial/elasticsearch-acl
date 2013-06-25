package org.elasticsearch.plugin.acl

import com.searchly.SearchlyModule
import com.searchly.node.SearchlyNode
import com.searchly.repository.facade.AccountFacade
import com.searchly.repository.facade.hazelcast.HazelCastAccountFacade
import org.elasticsearch.common.collect.ImmutableList
import org.elasticsearch.common.component.LifecycleComponent
import org.elasticsearch.common.inject.Module
import org.elasticsearch.common.inject.Modules
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.plugins.AbstractPlugin

/**
 * @author ferhat
 */
class AclPlugin extends AbstractPlugin {

    public String name() {
        return "acl";
    }

    public String description() {
        return "ACL Plugin";
    }

    @Override
    public Collection<Module> modules(Settings settings) {
        return ImmutableList.of(Modules.createModule(SearchlyModule.class, settings))
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Class<? extends LifecycleComponent>> services() {
        def services = []
        services << SearchlyNode.class
        services << AccountFacade.class
        return services
    }
}

