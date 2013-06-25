package com.searchly.plugin.acl

import org.elasticsearch.common.inject.Inject
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.plugins.AbstractPlugin

/**
 * @author ferhat
 */
class AclPlugin extends AbstractPlugin {

    private Settings settings;

    @Inject
    public AclPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    String name() {
        "ACL Plugin"
    }

    @Override
    String description() {
        "Adds secured endpoints for authentication and authorization"
    }
}
