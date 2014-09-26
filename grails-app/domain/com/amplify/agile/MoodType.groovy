package com.amplify.agile

import grails.rest.Resource

@Resource(uri='/api/moodtype', formats=['json'], readOnly = true)
class MoodType {

    static constraints = {
    }
	
	String link
	String display
}
