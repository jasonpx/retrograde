import com.amplify.agile.MoodType
import com.amplify.agile.RewardType
import com.amplify.agile.User

databaseChangeLog = {

    changeSet(author: "jpriest", id: "1411010958307-1") {
        grailsChange {
            change {
                User adminUser = new User(first: 'Admin', last: 'User', username: 'retrograde@amplify.com', password: "1234").save()

                MoodType happy = new MoodType(display: "happy", link: "images/mood_happy.png").save()
                MoodType sad = new MoodType(display: "sad", link: "images/mood_sad.png").save()
                MoodType irritated = new MoodType(display: "irritated", link: "images/mood_irritated.png").save()
                MoodType stressed = new MoodType(display: "stressed", link: "images/mood_stressed.png").save()
                MoodType tired = new MoodType(display: "tired", link: "images/mood_tired.png").save()
                MoodType smart = new MoodType(display: "smart", link: "images/mood_smart.png").save()
                MoodType energized = new MoodType(display: "energized", link: "images/mood_energized.png").save()

                RewardType getItTogether = new RewardType(display: "Get it Together!", link: '/retrograde/images/sticker_cat.png').save()
                RewardType youTried = new RewardType(display: "You tried", link: '/retrograde/images/sticker_youTried.png').save()
                RewardType goodJob = new RewardType(display: "Good Job!", link: '/retrograde/images/sticker_goodJob.png').save()
                RewardType superman = new RewardType(display: "Superman", link: '/retrograde/images/sticker_superman.png').save()
                RewardType scratch = new RewardType(display: "Scratch n' Sniff", link: '/retrograde/images/sticker_scratch.png').save()
            }
        }
    }
}