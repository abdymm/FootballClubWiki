package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context
import com.abdymalikmulky.fooball.footballclubwiki.util.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamLeagueCallback) {
        footballRemote.loadTeamLeague(leagueId, callback)
    }

    override fun loadEventLeague(isPastEvenet: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
        footballRemote.loadEventLeague(isPastEvenet, leagueId, callback)
    }


}