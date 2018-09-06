package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context
import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


class FootballRepo(context: Context) : FootballDataSource {



    internal var context: Context
    internal var footballRemote: FootballRemote
    internal var footballLocal: FootballLocal


    init {
        this.context = context
        this.footballLocal = FootballLocal(context)
        this.footballRemote = FootballRemote(context)
    }

    override fun loadLeague(callback: FootballDataSource.LoadLeagueCallback) {
        //handling offline first app, local and network *later
        footballRemote.loadLeague(callback)
    }

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamsCallback) {
        footballRemote.loadTeamLeague(leagueId, object : FootballDataSource.LoadTeamsCallback {
            override fun onLoaded(teams: List<Team>) {
                saveTeams(teams, leagueId)
                callback.onLoaded(teams)
            }

            override fun onFailed(errorMsg: String) {
                callback.onFailed(errorMsg)
            }

        })
    }

    override fun setFavoriteTeam(favorite: Boolean, teamId: String, callback: FootballDataSource.SetFavoriteTeamCallback) {
        footballLocal.setFavoriteTeam(favorite, teamId, callback)
    }

    private fun saveTeams(teams: List<Team>, leagueId: String) {
        for (team in teams) {
            footballLocal.isTeamExist(team.teamId, object : FootballDataSource.IsTeamExistCallback{
                override fun onTeamExisted() {
                    //may put some update function, later
                }
                override fun onTeamNotExisted() {
                    footballLocal.saveTeam(team, leagueId)
                }
            })
        }
    }

    override fun setFavoriteLeague(leagueId: String, callback: FootballDataSource.SetFavoriteLeagueCallback) {
        footballLocal.setFavoriteLeague(leagueId, callback)
    }

    override fun loadEventLeague(isPastEvenet: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
        footballRemote.loadEventLeague(isPastEvenet, leagueId, object : FootballDataSource.LoadEventLeagueCallback{
            override fun onLoaded(events: List<Event>) {
                events.forEachIndexed{index, event ->
                    val homeTeam = footballLocal.getTeam(event.idHomeTeam)
                    val awayTeam = footballLocal.getTeam(event.idAwayTeam)

                    events.get(index).homeTeamBadge = homeTeam.teamBadge
                    events.get(index).awayTeamBadge = awayTeam.teamBadge
                }
                callback.onLoaded(events)
            }

            override fun onFailed(errorMsg: String) {
                callback.onFailed(errorMsg)
            }

        })
    }

    override fun setFavoriteEvent(favorite: Boolean, eventId: String, callback: FootballDataSource.SetFavoriteEventCallback) {
        footballLocal.setFavoriteEvent(favorite, eventId, callback)
    }

    override fun loadTeam(teamId: String, callback: FootballDataSource.LoadTeamCallback) {
        footballRemote.loadTeam(teamId, callback)
    }

    override fun loadFavoriteEvent(callback: FootballDataSource.LoadFavEventLeagueCallback) {
        footballLocal.loadFavoriteEvent(callback)
    }

    override fun isEventHasFavorited(eventId: String, callback: FootballDataSource.IsEventFavLeagueCallback) {
        footballLocal.isEventHasFavorited(eventId, callback)
    }

}