package com.amplify.agile

import grails.transaction.Transactional

@Transactional
class UserService {
	
	def springSecurityService
	
    def getByTeam(Long teamId) {
        def t = Team.get(teamId)
        return t.users?.sort { it.username.toLowerCase() }
    }
	
	def getCurrentUser(){
		springSecurityService.currentUser
	}

    def updateUser(Long id, username, first, last, password) {
        def user = User.get(id)
        if (user)
        {
            user.username = username
            user.first = first
            user.last = last
            if (password) {
                user.password = password
            }
            user.save()
        }
    }
}
