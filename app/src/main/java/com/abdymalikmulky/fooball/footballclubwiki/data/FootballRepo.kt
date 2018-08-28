package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context


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
        footballRemote.loadTeamLeague(leagueId, callback)
    }

    override fun loadEventLeague(isPastEvenet: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
        footballRemote.loadEventLeague(isPastEvenet, leagueId, callback)
    }

    override fun loadTeam(teamId: String, callback: FootballDataSource.LoadTeamCallback) {
        footballRemote.loadTeam(teamId, callback)
    }

}