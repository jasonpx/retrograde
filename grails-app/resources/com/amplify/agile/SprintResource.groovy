package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/sprint')
@Produces('application/json')
class SprintResource extends BaseService {

    def sprintService
    def springSecurityService

    @GET
    @Path("/")
    Response list() {
        return Response.ok().entity(sprintService.list()).build()
    }
	
	@GET
	@Path("/listAvailable/{teamId}")
	Response listAvailable(@PathParam('teamId') Long teamId) {
		return Response.ok().entity(sprintService.listAvailable(teamId)).build()
	}
}
