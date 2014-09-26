package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import grails.converters.JSON

@Path('/api/retro')
@Produces('application/json')
class RetroResource {

    def retroService

    @GET
    @Path("/user/{userId}")
    Response getByUser(@PathParam('userId') Long userId) {
        return Response.ok().entity(retroService.getByUser(userId)).build()
    }

    @GET
    @Produces('text/html')
    @Path('/{retroId}/export')
    Response export(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(retroService.export(retroId)).build()
    }
	
	@GET
	@Path("/currentUser")
	Response getByCurrentUser() {
		JSON.use('deep')
		return Response.ok().entity(retroService.getByCurrentUser()).build()
	}

    @GET
    @Path("/moderator/{retroId}")
    Response getModerator(@PathParam('retroId') Long retroId) {
        JSON.use('deep')
        return Response.ok().entity(retroService.getModerator(retroId)).build()
    }

    @GET
    @Path("/{retroId}/name")
    Response getRetroName(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(retroService.getRetroName(retroId)).build()
    }

    @GET
    @Path("/{retroId}/status")
    Response getStatus(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(retroService.getStatus(retroId)).build()
    }

    @POST
    @Path("/{retroId}")
    Response finishRetro(@PathParam('retroId') Long retroId) {
        if (retroService.finishRetro(retroId)) {
            return Response.ok().entity().build()
        }

        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @POST
    @Path("/create/{sprintId}/{teamId}")
    Response createRetro(@PathParam('sprintId') Long sprintId, @PathParam('teamId') Long teamId) {
		def retro = retroService.create(sprintId,teamId)
        return Response.ok().entity(retro).build()
    }
}
