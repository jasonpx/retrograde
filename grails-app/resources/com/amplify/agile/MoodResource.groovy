package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/mood')
@Produces('application/json')
class MoodResource {

    def moodService

    @GET
    @Path('/retro/{retroId}')
    Response getByRetro(@PathParam('retroId') Long retroId) {
        Response.ok().entity(moodService.getByRetro(retroId).toList()).build()
    }

    @GET
    @Path('/sprint/{sprintId}')
    Response getBySprint(@PathParam('sprintId') Long sprintId) {
        Response.ok().entity(moodService.getBySprint(sprintId).toList()).build()
    }

    @POST
    @Path('/retro/{retroId}/{moodTypeId}')
    Response voteForRetro(@PathParam('retroId') Long retroId, @PathParam('moodTypeId') Long moodTypeId) {
        // Create mood vote entry for user
        try {
            // Vote!
            moodService.voteForRetro(retroId, moodTypeId)

            // Return success
            Response.ok().build()
        } catch (ex) {
            // Either the retro or mood type wasn't found
            // TODO: return better error message
            Response.status(Response.Status.NOT_FOUND).build()
        }

    }
}
