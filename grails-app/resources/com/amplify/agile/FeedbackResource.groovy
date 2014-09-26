package com.amplify.agile

import grails.converters.JSON

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT;
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

import javax.ws.rs.core.Response

@Path("/api/feedback")
@Produces("application/json")
public class FeedbackResource {

    def feedbackService

    @GET
    @Path("/{retroId}")
    public Response getAll(@PathParam('retroId') Long retroId) {
        JSON.use('deep')
        return Response.ok().entity(feedbackService.getAll(retroId)).build()
    }

    @GET
    @Path("/sprint/{sprintId}")
    public Response getAllForSprint(@PathParam('sprintId') Long sprintId) {
        JSON.use('deep')
        return Response.ok().entity(feedbackService.getAllForSprint(sprintId)).build()
    }

    @GET
    @Path("/well/{retroId}")
    public Response getWell(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(feedbackService.getWell(retroId)).build()
    }

    @GET
    @Path("/bad/{retroId}")
    public Response getBad(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(feedbackService.getBad(retroId)).build()
    }

    @GET
    @Path("/change/{retroId}")
    public Response getChange(@PathParam('retroId') Long retroId) {
        return Response.ok().entity(feedbackService.getChange(retroId)).build()
    }

    @POST
    @Consumes('application/json')
    @Path("/well/{retroId}")
    public Response sendWell(@PathParam('retroId') Long retroId, Map body) {
        def text = body.get('text')
        def f = feedbackService.create(retroId, FeedbackType.WELL, text)
        feedbackService.sendMessage(MessageTopic.WELL, new Message(content: [type: "new_feedback"]))
        return Response.ok().entity().build()
    }

    @POST
    @Consumes('application/json')
    @Path("/bad/{retroId}")
    public Response sendBad(@PathParam('retroId') Long retroId, Map body) {
        def text = body.get('text')
        def f = feedbackService.create(retroId, FeedbackType.BAD, text)
        feedbackService.sendMessage(MessageTopic.BAD, new Message(content: [type: "new_feedback"]))
        return Response.ok().entity().build()
    }

    @POST
    @Consumes('application/json')
    @Path("/change/{retroId}")
    public Response sendChange(@PathParam('retroId') Long retroId, Map body) {
        def text = body.get('text')
        def f = feedbackService.create(retroId, FeedbackType.CHANGE, text)
        feedbackService.sendMessage(MessageTopic.CHANGE, new Message(content: [type: "new_feedback"]))
        return Response.ok().entity().build()
    }

    @POST
    @Path("/bad/{retroId}")
    public Response sendBad(@PathParam('retroId') Long retroId) {
        return Response.ok().entity().build()
    }

    @POST
    @Path("/change/{retroId}")
    public Response sendChange(@PathParam('retroId') Long retroId) {
        return Response.ok().entity().build()
    }

	@GET
	@Path("/lastRetroItems/{currentRetroId}")
	public Response getLastRetroItems(@PathParam('currentRetroId') Long currentRetroId) {
		return Response.ok().entity(feedbackService.getLastRetroItems(currentRetroId)).build()
	}
	
	@GET
	@Path("/getEmpty")
	public Response getEmpty() {
		Feedback feedback = new Feedback(retro: new Retro(), text: "",user: new User(), type: FeedbackType.WELL)
        return Response.ok().entity(feedback).build()
    }

    @POST
    @Path("/completed/{feedbackId}/{bool}")
    def markCompleted(@PathParam('feedbackId') Long feedbackId, @PathParam('bool') Boolean bool) {
        Response.ok().entity(feedbackService.markCompleted(feedbackId, bool)).build()
    }

    @POST
    @Path("/discussed/{feedbackId}/{bool}")
    def markDiscussed(@PathParam('feedbackId') Long feedbackId, @PathParam('bool') Boolean bool) {
        Response.ok().entity(feedbackService.markDiscussed(feedbackId, bool)).build()
    }

    @POST
    @Path("/vote/{feedbackId}/{vote}")
    def vote(@PathParam('feedbackId') Long feedbackId, @PathParam('vote') Integer vote) {
        def feedback = feedbackService.vote(feedbackId, vote)
        if (feedback) {
            def messageTopic = null
            switch (feedback.type) {
                case FeedbackType.WELL:
                    messageTopic = MessageTopic.WELL
                    break;
                case FeedbackType.BAD:
                    messageTopic = MessageTopic.BAD
                    break;
                case FeedbackType.CHANGE:
                    messageTopic = MessageTopic.CHANGE
                    break;
            }

            feedbackService.sendMessage(messageTopic, new Message(content: [type: "vote"]))
        }

        Response.ok(feedback).entity().build()
    }

    @POST
    @Consumes('application/json')
	@Path("save")
    def save(Map body) {
		body = updateFeedbackType(body)
        Feedback feedback = new Feedback(body)
        feedbackService.create(feedback)
		if(feedback.hasErrors()){
			return errorResponse(feedback)
		}
        return Response.ok().entity(feedback).build()
    }
	
	//can we put this in a super class?
	def errorResponse(def domainObject){		
		if(!domainObject.hasErrors()){
			return
		}
		def errors = []
		domainObject.errors.each{
			errors.add(it.toString())
		}
		log.error("errors creating object: ${domainObject.errors}");
		return Response.serverError().entity(errors).build()
	}
	
	//hack to get around the default marshalling not being able to handle the enum
	def updateFeedbackType(Map body){
		def type = body.get("type")
		if(type){
			body.'type' = type.name
		}
		return body
	}

}
