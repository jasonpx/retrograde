package com.amplify.agile

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils


@Transactional
class RetroService extends BaseService {

    def teamService
    def meterService
    def moodService
	
    /*
    This method will get all retros to which the user has access.  This includes the retros to which the user's current
    team has access, as well as any retros in which the user has been added to the retro (upon closing; to keep historical
    record of users in that retro at the time)
     */
    def getByUser(Long userId) {
        def c = Retro.createCriteria()
        List<Retro> results = c.list {
            users {
                idEq(userId)
            }
        }
        List<Team> teams = teamService.getByUser(userId)
		teams.each{
			results.addAll(Retro.findAllByTeam(it))
		}
        return results.unique().sort{it.sprint.startDate}
    }

    def export(Long retroId) {
        GrailsWebRequest webUtils = WebUtils.retrieveGrailsWebRequest()
        def request = webUtils.getCurrentRequest()
        def root = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()

        def retro = Retro.findById(retroId)
        return reportTemplate
                .replaceAll("@site_root", root)
                .replaceAll("@team_name", retro.team.name)
                .replaceAll("@sprint_name", retro.sprint.name)
                .replaceAll("@sprint_startDate", retro.sprint.startDate.getDateString())
                .replaceAll("@sprint_endDate", retro.sprint.endDate.getDateString())
                .replaceAll("@sprint_score", (Math.round(meterService.getScoreByRetro(retroId)) / 10).toString())
                .replaceAll("@team_members", reportTeamMembers(retro))
                .replaceAll("@last_retro", reportLastRetro(retro))
                .replaceAll("@went_well", reportFeedback(retro, FeedbackType.WELL))
                .replaceAll("@went_poorly", reportFeedback(retro, FeedbackType.BAD))
                .replaceAll("@want_to_change", reportFeedback(retro, FeedbackType.CHANGE))
                .replaceAll("@mood", reportMood(retro))
    }

    def reportTeamMembers(Retro retro) {
        def str = ""

        retro.team.users.each { it ->
            str += "<li>" + it.first + " " + it.last + "</li>"
        }

        str
    }

    def reportLastRetro(Retro retro) {
        def str = ""

        def feedback = []
        if (retro.previousRetro) {
            feedback = Feedback.findAllByRetroAndType(retro.previousRetro, FeedbackType.CHANGE)
        }

        feedback.each { it ->
            str += "<li class=\"" + (it.completed ? "done" : "") + "\">" + it.text + "</li>"
        }

        str
    }

    def reportFeedback(Retro r, FeedbackType typeTarget) {
        def str = ""
        def feeds = Feedback.findAll { retro.id == r.id && type == typeTarget }

        feeds.each { it ->
            str += "<li>" + it.text + "</li>"
        }

        str
    }

    def reportMood(Retro r) {
        def str = ""
        def moods = MoodType.findAll()
        def votes = moodService.getByRetro(r.id)

        moods.each { mood ->
            def count = 0
            votes.each { vote -> if (vote.moodTypeId == mood.id) ++count }
            if (count) {
                str += "<li>" + mood.display + ": " + count + "</li>"
            }
        }

        str
    }

    def getModerator(Long retroId) {
        def r = Retro.findById(retroId)
        if (r) {
            return r.moderator
        }

        null
    }
	
	def getByCurrentUser(){
		getByUser(getCurrentUser().id)
	}

    def getRetroName(Long retroId) {
        def retro = Retro.findById(retroId)
        if (retro) {
            return retro.team.name + ": " + retro.sprint.name
        }
        null
    }

    def getStatus(Long retroId) {
        def retro = Retro.findById(retroId)
        if (retro) {
            return [status: retro.status]
        }
        null
    }

    def finishRetro(Long retroId) {
        def retro = Retro.findById(retroId)
        if (!retro) {
            return false
        }

        if (retro.status != Retro.RetroStatus.COMPLETE) {
            retro.status = Retro.RetroStatus.COMPLETE
            retro.team.users.each { u ->
                retro.addToUsers(u)
            }
            retro.save(failOnError: true)
        }
        sendMessage(MessageTopic.FINISHRETRO)
        true
    }
	
	def create(Long sprintId, Long teamId) {
		Sprint sprint = Sprint.get(sprintId)
		Team team = Team.get(teamId)


        // Ugly but it's Sunday and I just want it to work
        def lastRetro = null
        def teamRetros = Retro.findAllByTeam(team)
        if (teamRetros) {
            teamRetros.each { it ->
                if (lastRetro == null || it.sprint.startDate > lastRetro.sprint.startDate) {
                    lastRetro = it;
                }
            }
        }
		Retro retro = new Retro(sprint:sprint, team:team, moderator:getCurrentUser(), previousRetro: lastRetro).save()
        sendMessage(MessageTopic.NEWRETRO)
        retro
	}


    private def reportTemplate =
        "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title></title>" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"@site_root/js/bootstrap/css/bootstrap.min.css\"/>" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"@site_root/js/bootstrap/css/bootstrap-theme.min.css\"/>" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"@site_root/js/font-awesome/css/font-awesome.min.css\"/>" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"http://fonts.googleapis.com/css?family=Lobster\">" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"@site_root/css/application.css\"/>" +
                "    <style>" +
                "        h3, h5 {" +
                "            text-align: center;" +
                "        }" +
                "" +
                "        h6 {" +
                "            padding-top: 25px;" +
                "            font-size: 14pt;" +
                "        }" +
                "" +
                "        body {" +
                "            background-color: #fafafa;" +
                "            font-size: 10pt;" +
                "        }" +
                "" +
                "        .done {" +
                "            text-decoration: line-through;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div style=\"width: 800px; margin: auto; padding-top: 25px;\">" +
                "        <h1 style=\"font-size: 50px; font-family: 'Lobster', cursive; margin-top: -8px; text-align: center\">Retrograde</h1>" +
                "<h3>Retrospective notes</h3>" +
                "<h5>@team_name - @sprint_name</h5>" +
                "" +
                "        <h6>Sprint Information</h6>" +
                "        @sprint_name was from @sprint_startDate to @sprint_endDate. On a scale from 1 to 10 (10 being awesome), the team rated the sprint at <b>@sprint_score</b>." +
                "        <div>The team members are:</div>" +
                "        <ul>" +
                "            @team_members" +
                "        </ul>" +
                "" +
                "        <h6>From Last Retro</h6>" +
                "        <div>Here are the action item(s) listed from the previous retrospective:</div>" +
                "        <ul style=\"padding-top: 10px\">" +
                "            @last_retro" +
                "        </ul>" +
                "" +
                "        <h6>What went well</h6>" +
                "        <div>Here are the things that went well during the sprint:</div>" +
                "        <ul style=\"padding-top: 10px\">" +
                "            @went_well" +
                "        </ul>" +
                "" +
                "        <h6>What went poorly</h6>" +
                "        <div>Here are the things that went poorly during the sprint:</div>" +
                "        <ul style=\"padding-top: 10px\">" +
                "            @went_poorly" +
                "        </ul>" +
                "" +
                "        <h6>What we'd like to change</h6>" +
                "        <div>Here are the things that we would like to change for the next sprint:</div>" +
                "        <ul style=\"padding-top: 10px\">" +
                "            @want_to_change" +
                "        </ul>" +
                "" +
                "        <h6>Team Mood</h6>" +
                "        <div>Here is an insight on how the team felt during the sprint:</div>" +
                "        <ul style=\"padding-top: 10px\">" +
                "            @mood" +
                "        </ul>" +
                "    </div>" +
                "</body>" +
                "</html>"
}
