package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/reward')
@Produces('application/json')
class RewardResource {

    def rewardService

    @GET
    @Path('/retro/{retroId}')
    Response getByRetro(@PathParam('retroId') Long retroId) {
        Response.ok().entity(rewardService.getByRetro(retroId)).build()
    }

    @POST
    @Path('/retro/{retroId}/{rewardTypeId}/{recipientId}')
    Response voteForRetro(@PathParam('retroId') Long retroId, @PathParam('rewardTypeId') Long rewardTypeId, @PathParam('recipientId') Long recipientId) {
        try {
            // Give a reward
            rewardService.giveReward(retroId, rewardTypeId, recipientId)

            // Return success
            Response.ok().build()
        } catch (ex) {
            // Either the retro or reward type wasn't found
            // TODO: return better error message
            println ex.message
            Response.status(Response.Status.NOT_FOUND).build()
        }

    }
}
