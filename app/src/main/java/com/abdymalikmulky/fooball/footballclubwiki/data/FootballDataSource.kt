package com.abdymalikmulky.fooball.footballclubwiki.data

import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


interface FootballDataSource {

    fun loadLeague(callback: LoadLeagueCallback)

    fun loadTeamLeague(leagueId: String, callback: LoadTeamsCallback)

    fun loadTeam(teamId: String, callback: LoadTeamCallback)

    fun loadEventLeague(isPastEvenet: Boolean, leagueId: String, callback: LoadEventLeagueCallback)

    interface LoadLeagueCallback {
        fun onLoaded(leagues: List<League>)
        fun onFailed(errorMsg: String)
    }

    interface LoadTeamsCallback {
        fun onLoaded(teams: List<Team>)
        fun onFailed(errorMsg: String)
    }

    interface LoadTeamCallback {
        fun onLoaded(team: Team)
        fun onFailed(errorMsg: String)
    }

    interface LoadEventLeagueCallback {
        fun onLoaded(events: List<Event>)
        fun onFailed(errorMsg: String)
    }

}