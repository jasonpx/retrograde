package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/team')
@Produces('application/json')
class TeamResource {

    def teamService

    @GET
    @Path('/user/{userId}')
    Response getByUser(@PathParam('userId') Long userId) {
        Response.ok().entity(teamService.getByUser(userId)).build()
    }
	
	@GET
	@Path('/user/current')
	Response getByUser() {
		Response.ok().entity(teamService.getByCurrentUser()).build()
	}
}
