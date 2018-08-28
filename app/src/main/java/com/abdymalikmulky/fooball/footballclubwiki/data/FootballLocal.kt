package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context
import com.abdymalikmulky.fooball.footballclubwiki.util.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FootballLocal(context: Context) : FootballDataSource {



    internal var context: Context


    init {
        this.context = context
    }

    override fun loadLeague(callback: FootballDataSource.LoadLeagueCallback) {
        //local db
    }

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamLeagueCallback) {

    }

    override fun loadEventLeague(isPastEvent: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
    }
}