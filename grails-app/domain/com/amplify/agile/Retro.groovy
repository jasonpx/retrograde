package com.amplify.agile

class Retro {

    static constraints = {
        previousRetro nullable: true
    }

    static hasMany = [users: User]
	
	enum RetroStatus {IN_PROGRESS,COMPLETE}
	
	RetroStatus status = RetroStatus.IN_PROGRESS 
	User moderator
	Sprint sprint
	Team team
    Retro previousRetro
    List<User> users = []
}
