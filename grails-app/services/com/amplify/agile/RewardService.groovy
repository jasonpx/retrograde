package com.amplify.agile

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class RewardService extends BaseService {

    def getByRetro(Long retroId) {
        def retro = Retro.findById(retroId)
        def data = Reward.findAllByRetro(retro)
        def rewards = []
        data.each { it -> rewards.add([rewardTypeId: it.type.id, rewardLink: it.type.link, recipientId: it.recipient.id, senderId: it.sender.id, retroId: it.retro.id]) }

        rewards.toList()
    }

    def giveReward(Long retroId, Long rewardTypeId, Long recipientId) {
        // Get current user
        def sender = currentUser

        // Load recipient
        def recipient = User.findById(recipientId)
        if (!recipient) {
            throw new IllegalArgumentException("recipientId")
        }

        // Load retro
        def retro = Retro.findById(retroId)
        if (!retro) {
            throw new IllegalArgumentException("retroId")
        }

        // Load mood type
        def rewardType = RewardType.findById(rewardTypeId)
        if (!rewardType) {
            throw new IllegalArgumentException("rewardTypeId")
        }

        // TODO: How many badges can a user give? Can we delete them? Right now, the front-end prevent this but it should be on the back-end as well
        new Reward(retro: retro, type: rewardType, recipient: recipient, sender: sender).save()

        try {
            def rewards = getByRetro(retroId) as JSON
            sendMessage(MessageTopic.REWARD, rewards.toString(false).bytes.encodeBase64().toString())
        } catch (ex) {
            println "Exception while sending message: " + ex.message
        }
    }
}
