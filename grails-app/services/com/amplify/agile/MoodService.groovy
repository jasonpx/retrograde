package com.amplify.agile

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class MoodService extends BaseService {

    def getByRetro(Long retroId) {
        List<Mood> data = Mood.executeQuery(
                "select m from Mood as m \
                where m.retro.id = :retroId \
                ", [retroId: retroId])

        def moods = []
        data.each { it -> moods.add([moodTypeId: it.moodType.id, userId: it.user.id]) }

        moods
    }

    def getBySprint(Long sprintId) {
        List<Mood> data = Mood.executeQuery(
                "select m from Mood as m \
                where m.retro.sprint.id = :sprintId \
                ", [sprintId: sprintId])

        def moods = []
        data.each { it -> moods.add([moodTypeId: it.moodType.id, userId: it.user.id]) }

        moods
    }

    def voteForRetro(Long retroId, Long moodTypeId) {
        // TODO: replace hard-coded user for current user
        //def user = User.findByUsername("fbenoit@amplify.com")
        def user = currentUser

        // Load retro
        def retro = Retro.findById(retroId)
        if (!retro) {
            throw new IllegalArgumentException("retroId")
        }

        // Load mood type
        def moodType = MoodType.findById(moodTypeId)
        if (!moodType) {
            throw new IllegalArgumentException("moodTypeId")
        }

        // Check if user has already voted. If so, update his vote
        // just a personal preference style here
        def curVote = Mood.findByUserAndRetro(user, retro) ?: new Mood(retro: retro, moodType: moodType, user: user).save()
        curVote.moodType = moodType
        curVote.save()

        try {
            def moods = getByRetro(retroId) as JSON
            sendMessage(MessageTopic.MOOD, moods.toString(false).bytes.encodeBase64().toString())
        } catch (ex) {
            println "Exception while sending message: " + ex.message
        }
    }
}
