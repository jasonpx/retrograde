package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/meter')
@Produces('application/json')
class MeterResource {

    def meterService

    @GET
    @Path("/retro/{retroId}")
    Response getScoreByRetro(@PathParam('retroId') Long retroId) {
        def score = meterService.getScoreByRetro(retroId)
        def userScore = meterService.getCurrentUsersScoreByRetro(retroId)
        def obj = [score: score, user: userScore]
        Response.ok().entity(obj).build()
    }

    @GET
    @Path("/sprint/{sprintId}")
    Response getScoreBySprint(@PathParam('sprintId') Long sprintId) {
        def score = meterService.getScoreBySprint(sprintId)
        def obj = [score: score, user: null]
        Response.ok().entity(obj).build()
    }

    @POST
    @Path("/retro/{retroId}/{score}")
    Response setScoreForUser(@PathParam('retroId') Long retroId, @PathParam('score') Integer score) {
        try {
            Response.ok().entity(meterService.setScoreForUser(retroId, score)).build()
        }
        catch(IllegalArgumentException ex) {
            def e = new Errors(errors: [ex])
            Response.serverError().entity(e).build()
        }

    }

}
