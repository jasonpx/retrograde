package com.amplify.agile

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/user')
@Produces('application/json')
class UserResource extends BaseService {

    def userService
    def springSecurityService

    @GET
    @Path("/team/{teamId}")
    Response getUsersByTeam(@PathParam('teamId') Long teamId) {
        return Response.ok().entity(userService.getByTeam(teamId)).build()
    }

	@GET
	@Path("/current")
	Response getCurrentUser() {
		return Response.ok().entity(userService.currentUser).build()
	}

//    @GET
//    @Path("/current")
//    Response getCurrentUser() {
//        return Response.ok().entity(springSecurityService.getCurrentUser()).build()
//    }

    @POST
    @Path("/update/{userId}")
    Response updateUser(@PathParam('userId') Long userId, Map body) {
        def username = body.get('username')
        def first = body.get('first')
        def last = body.get('last')
        def password = body.get('password')
        return Response.ok().entity(userService.updateUser(userId, username, first, last, password)).build()
    }
}
