package com.amplify.agile

class Feedback {

    static constraints = {
	
    }

    static hasMany = [votes: Vote]

    List<Vote> votes = []
	FeedbackType type
	User user
	boolean completed = false
    boolean discussed = false
	String text
	Retro retro
}

enum FeedbackType {
    WELL,
    BAD,
    CHANGE
}
