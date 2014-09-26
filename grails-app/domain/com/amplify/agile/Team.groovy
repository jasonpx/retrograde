package com.amplify.agile

class Team {

    static constraints = {
    }
	
	List<User> users = []
	String name
	
	static hasMany = [users:User]
}
