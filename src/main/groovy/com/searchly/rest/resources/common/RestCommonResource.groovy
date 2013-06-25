package com.searchly.rest.resources.common

import com.searchly.rest.resources.base.RestBaseResource

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

/**
 @author ferhat
 */
@Path("/")
class RestCommonResource extends RestBaseResource {
    @GET
    @Path("")
    @Produces("application/json")
    public String common() {
        return '{"ok": true,"status": 200,"name": "searchly","version": {"number": "0.90.1","snapshot_build": false}}'
    }
}
