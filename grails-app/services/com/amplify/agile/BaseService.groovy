package com.amplify.agile

import grails.transaction.Transactional

@Transactional
class BaseService {

    def springSecurityService
    def brokerMessagingTemplate

    def getCurrentUser() {
        return springSecurityService.currentUser
    }

    def sendMessage(def topic, def msg) {
        brokerMessagingTemplate.convertAndSend("/topic/${topic}".toString(), msg)
    }

    def sendMessage(def topic) {
        sendMessage(topic, "")
    }
}
