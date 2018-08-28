package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context


class FootballLocal(context: Context) : FootballDataSource {

    internal var context: Context


    init {
        this.context = context
    }

    override fun loadLeague(callback: FootballDataSource.LoadLeagueCallback) {
        //local db
    }

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamsCallback) {

    }

    override fun loadEventLeague(isPastEvent: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
    }

    override fun loadTeam(teamId: String, callback: FootballDataSource.LoadTeamCallback) {
    }
}