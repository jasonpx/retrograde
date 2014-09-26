package com.amplify.agile

import grails.rest.Resource

@Resource(uri='/api/rewardtype', formats=['json'], readOnly = true)
class RewardType {

    static constraints = {
    }
	
	String display //star, poop hat
    String link
}
