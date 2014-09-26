package com.amplify.agile

import grails.transaction.Transactional

@Transactional
class TeamService extends BaseService {

    def getByUser(Long userId) {
        def c = Team.createCriteria()
        def results = c.list {
            users {
                idEq(userId)
            }
        }
        return results?.sort { it.name.toLowerCase() }
    }
	
	def getByCurrentUser(){
		getByUser(getCurrentUser().id)
	}
}
