package com.amplify.agile

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class FeedbackService extends BaseService {

    def getAll(Long retroId) {
        JSON.use('deep')
        return getFeedbackByRetroAndType(retroId, null)
    }

    def getAllForSprint(Long sprintId) {
        JSON.use('deep')
        return getFeedbackBySprintAndType(sprintId, null)
    }

    def getWell(Long retroId) {
        JSON.use('deep')
        return getFeedbackByRetroAndType(retroId, FeedbackType.WELL)
    }

    def getBad(Long retroId) {
        JSON.use('deep')
        return getFeedbackByRetroAndType(retroId, FeedbackType.BAD)
    }

    def getChange(Long retroId) {
        JSON.use('deep')
        return getFeedbackByRetroAndType(retroId, FeedbackType.CHANGE)
    }

    def vote(Long feedbackId, Integer vote) {
        def feedback = Feedback.get(feedbackId)
        def cVote = feedback.votes.find { it.user == currentUser}
        if(!cVote) {
            cVote = new Vote(user: currentUser)
            feedback.addToVotes(cVote)
        }
        cVote.value = vote
        feedback.save()
    }

    def markCompleted(Long feedbackId, Boolean bool) {
        Feedback f = Feedback.get(feedbackId)
        f.completed = bool
        f.save()
        sendMessage(MessageTopic.LASTRETRO, new Message(content: [id: feedbackId, completed: f.completed]))
        return f
    }

    def markDiscussed(Long feedbackId, Boolean discussed) {
        Feedback f = Feedback.get(feedbackId)
        f.discussed = discussed
        f.save()

        def topic = MessageTopic.WELL
        if (f.type == FeedbackType.BAD) {
            topic = MessageTopic.BAD
        } else if (f.type == FeedbackType.CHANGE) {
            topic = MessageTopic.CHANGE
        }

        sendMessage(topic, new Message(content: [type: "discussed"]))
        return f
    }

    def create(Long retroId, FeedbackType feedbackType, String text) {
        Retro r = Retro.get(retroId)
        def f = new Feedback(retro: r, user: currentUser, text: text, type: feedbackType)
        f.save()
        return f
    }

	def create(Feedback feedback){
		return feedback.save()
	}
	
    def getLastRetroItems(Long currentRetroId) {
        Retro currentRetro = Retro.get(currentRetroId)
        if(!currentRetro.previousRetro)
            return []
        return getChange(currentRetro.previousRetro.id)
    }

    private def getFeedbackByRetroAndType(Long retroId, FeedbackType feedbackType) {
        if (!feedbackType) {
            return Feedback.findAll { retro.id == retroId }
        }

        Feedback.findAll { retro.id == retroId && type == feedbackType}
    }

    private def getFeedbackBySprintAndType(Long sprintId, FeedbackType feedbackType) {
        if (!feedbackType) {
            return Feedback.findAll { retro.sprint.id == sprintId }
        }

        Feedback.findAll { retro.sprint.id == sprintId && type == feedbackType}
    }
	
}
