package com.amplify.agile

import grails.transaction.Transactional

@Transactional
class MeterService extends BaseService {

    def getScoreByRetro(Long retroId) {
        def retro = Retro.findById(retroId)
        def meters = Meter.findAllByRetro(retro)

        if (meters.size()) {
            return meters.score.sum() / meters.size()
        }

        -1
    }

    def getCurrentUsersScoreByRetro(Long retroId) {
        Retro retro = Retro.get(retroId)
        def meter = Meter.findByUserAndRetro(currentUser, retro)
        if(!meter)
            return null
        return meter.score
    }

    def getScoreBySprint(Long sprintId) {
        def c = Meter.createCriteria()
        def meters = c.list {
            retro {
                sprint {
                    idEq(sprintId)
                }
            }
        }

        if (meters.size()) {
            return meters.score.sum() / meters.size()
        }

        -1
    }

    def setScoreForUser(Long retroId, Integer score) {
        // Load retro
        def retro = Retro.findById(retroId)
        if (!retro) {
            throw new IllegalArgumentException("retroId")
        }

        def curMeter = Meter.findByUserAndRetro(currentUser, retro) ?: new Meter(retro: retro, user: currentUser)
        curMeter.score = score
        curMeter.save()
        sendMessage(MessageTopic.METER, getScoreByRetro(retroId))
        return curMeter
    }
}
