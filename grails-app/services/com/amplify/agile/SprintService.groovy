package com.amplify.agile

import grails.transaction.Transactional

@Transactional
class SprintService extends BaseService {

    def list() {
        Sprint.findAll()?.sort { it.name }
    }
	
	def listAvailable(Long teamId) {
		def allSprints = Sprint.findAll()?.sort { it.name.toLowerCase() }
		def result = []
		allSprints.each { Sprint s->
			def query = Retro.where {
				sprint.id == s.id
				team.id == teamId
			}
			Retro r = query.find()
			if (!r) {
				result.add(s)
			}
		}
		return result
	}
}
