import com.amplify.agile.Feedback;
import com.amplify.agile.Meter;
import com.amplify.agile.Mood;
import com.amplify.agile.MoodType;
import com.amplify.agile.Retro;
import com.amplify.agile.Reward;
import com.amplify.agile.RewardType;
import com.amplify.agile.Sprint;
import com.amplify.agile.Team;
import com.amplify.agile.FeedbackType;
import com.amplify.agile.Retro.RetroStatus;
import com.amplify.agile.User;
import com.amplify.agile.Vote;

class BootStrap {

    def init = { servletContext ->
    	//createTestData()
	}
	
    def destroy = {
    }
	
	def createTestData() {
		User jason = new User(first:'Jason',last:'Priest',username:'jpriest@amplify.com', password: "1234").save()
		User brian = new User(first:'Brian',last:'Bull',username:'bbull@amplify.com', password: "1234").save()
		User frank = new User(first:'Frank',last:'Benoit',username:'fbenoit@amplify.com', password: "1234").save()
		User jonathan = new User(first:'Jonathan',last:'Berry',username:'jberry@amplify.com', password: "1234").save()
		User emily = new User(first:'Emily',last:'Friese',username:'efriese@amplify.com', password: "1234").save()
		Team kicksAss  = new Team(name:"GEGT").save()
		Team superStars = new Team(name:"Trinity").save()
		kicksAss.users.add(jason)
		kicksAss.users.add(brian)
		kicksAss.users.add(frank)
		kicksAss.users.add(jonathan)
		kicksAss.users.add(emily)
		kicksAss.save()
		superStars.users.add(frank)
		superStars.users.add(jason)
		superStars.save()
		Sprint sprint2 = new Sprint(name:"Sprint 2",startDate:Date.parse("yyyy-MM-dd","2014-09-08"),endDate:Date.parse("yyyy-MM-dd","2014-09-15") ).save()
		Sprint sprint1 = new Sprint(name:"Sprint 1",startDate:Date.parse("yyyy-MM-dd","2014-09-01"),endDate:Date.parse("yyyy-MM-dd","2014-09-07") ).save()
		Sprint sprint3 = new Sprint(name:"Sprint 3",startDate:Date.parse("yyyy-MM-dd","2014-09-16"),endDate:Date.parse("yyyy-MM-dd","2014-09-30") ).save()
		Sprint sprint4 = new Sprint(name:"Sprint 4",startDate:Date.parse("yyyy-MM-dd","2014-10-01"),endDate:Date.parse("yyyy-MM-dd","2014-10-14") ).save()
		
		Retro retro1 = new Retro(moderator: jason,sprint: sprint1, team:kicksAss, status: RetroStatus.COMPLETE, ).save()
		Retro retro2 = new Retro(moderator: jason,sprint: sprint2, team:kicksAss, previousRetro: retro1, users: [jason, brian, jonathan, emily]).save()
		Retro superStarsRetro1 = new Retro(moderator: jason,sprint: sprint1, team:superStars, previousRetro: null, users: [jason, frank]).save()
		Feedback f1 = new Feedback(retro: retro1, text: "Collaboration",user: brian, type: FeedbackType.WELL).save()
		Feedback f2 = new Feedback(retro: retro1, text: "Completed So Many Points",user: jason, type: FeedbackType.WELL).save()
		Feedback f3 = new Feedback(retro: retro1, text: "Worked well together",user: emily, type: FeedbackType.WELL).save()
		Feedback f4 = new Feedback(retro: retro1, text: "Solved issues related to environments",user: frank, type: FeedbackType.WELL).save()
		Feedback f5 = new Feedback(retro: retro1, text: "Production issues interfered with story work",user: jason, type: FeedbackType.BAD).save()
		Feedback f6 = new Feedback(retro: retro1, text: "Technical issues on local dev box",user: brian, type: FeedbackType.BAD).save()
		Feedback f7 = new Feedback(retro: retro1, text: "We ran out of chocolate",user: emily, type: FeedbackType.BAD).save()
		Feedback f8 = new Feedback(retro: retro1, text: "Speed up loading time of the application",user: brian, type: FeedbackType.CHANGE).save()
		Feedback f9 = new Feedback(retro: retro1, text: "Get more chocolate",user: emily, type: FeedbackType.CHANGE).save()
		Feedback f10 = new Feedback(retro: retro1, text: "Get more beer",user: jonathan, type: FeedbackType.CHANGE).save()
		Feedback f11 = new Feedback(retro: retro1, text: "Increase test coverage",user: jason, type: FeedbackType.CHANGE).save()
        f3.addToVotes(new Vote(user:jonathan,value:1))
		f3.addToVotes(new Vote(user:brian,value:1))
		f3.addToVotes(new Vote(user:frank,value:1))
		f3.addToVotes(new Vote(user:jason,value:1))
		f7.addToVotes(new Vote(user:jason,value:-1))
		MoodType happy = new MoodType(display: "happy",link:"images/mood_happy.png").save()
		MoodType sad = new MoodType(display: "sad",link:"images/mood_sad.png").save()
		MoodType irritated = new MoodType(display: "irritated",link:"images/mood_irritated.png").save()
		MoodType stressed = new MoodType(display: "stressed",link:"images/mood_stressed.png").save()
		MoodType tired = new MoodType(display: "tired",link:"images/mood_tired.png").save()
		MoodType smart = new MoodType(display: "smart",link:"images/mood_smart.png").save()
		MoodType energized = new MoodType(display: "energized",link:"images/mood_energized.png").save()
		Mood m1 = new Mood(retro: retro1,moodType:irritated,user: emily ).save()
		Mood m2 = new Mood(retro: retro1,moodType:energized,user: brian ).save()
		Mood m3 = new Mood(retro: retro1,moodType:sad,user: jason ).save()
		Mood m4 = new Mood(retro: retro1,moodType:happy,user: jonathan ).save()
		Mood m5 = new Mood(retro: retro1,moodType:smart,user: frank ).save()
        Mood m6 = new Mood(retro: superStarsRetro1,moodType:happy,user: frank ).save()
        Mood m7 = new Mood(retro: superStarsRetro1,moodType:smart,user: jason ).save()
		Meter meter1 = new Meter(retro: retro1,score:33,user:emily).save()
		Meter meter2 = new Meter(retro: retro1,score:77,user:frank).save()
		Meter meter3 = new Meter(retro: retro1,score:44,user:jason).save()
		Meter meter4 = new Meter(retro: retro1,score:99,user:brian).save()
		Meter meter5 = new Meter(retro: retro1,score:11,user:jonathan ).save()
		RewardType getItTogether = new RewardType(display: "Get it Together!", link: '/retrograde/images/sticker_cat.png').save()
		RewardType youTried = new RewardType(display: "You tried", link: '/retrograde/images/sticker_youTried.png').save()
		RewardType goodJob = new RewardType(display: "Good Job!", link: '/retrograde/images/sticker_goodJob.png').save()
        RewardType superman = new RewardType(display: "Superman", link: '/retrograde/images/sticker_superman.png').save()
        RewardType scratch = new RewardType(display: "Scratch n' Sniff", link: '/retrograde/images/sticker_scratch.png').save()
		Reward reward1 = new Reward(retro: retro1,recipient: emily,sender:jonathan, type: getItTogether).save()
		Reward reward2 = new Reward(retro: retro1,recipient: jonathan,sender:brian, type: youTried).save()
		//Reward reward3 = new Reward(retro: retro1,recipient: frank,sender:jason, type: goodJob).save()
		Reward reward4 = new Reward(retro: retro1,recipient: frank,sender:brian, type: goodJob).save()
	}

}
