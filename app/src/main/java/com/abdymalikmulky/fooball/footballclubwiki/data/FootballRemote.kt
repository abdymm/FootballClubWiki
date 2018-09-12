package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.event.EventResponse
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.league.LeagueResponse
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.data.team.TeamResponse
import com.abdymalikmulky.fooball.footballclubwiki.util.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FootballRemote(context: Context) : FootballDataSource {


    internal var context: Context

    internal var footballApi: FootballApi

    init {
        this.context = context
        footballApi = ApiHelper.client()!!.create(FootballApi::class.java)
    }

    override fun loadLeague(callback: FootballDataSource.LoadLeagueCallback) {
        val call = footballApi.getLeagues()
        call.enqueue(object : Callback<LeagueResponse>{
            override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {
                callback.onFailed(t.localizedMessage)
            }

            override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
                if (response.isSuccessful) {
                    val bodyResponse = response.body()
                    val leagues: List<League>
                    leagues = bodyResponse!!.leagues

                    callback.onLoaded(leagues)
                } else {
                    callback.onFailed(response.message())
                }
            }

        })
    }

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamsCallback) {
        val call = footballApi.getTeams(leagueId)
        call.enqueue(object : Callback<TeamResponse>{
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                callback.onFailed(t.localizedMessage)
            }

            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                if (response.isSuccessful) {
                    val bodyResponse = response.body()
                    val teams: List<Team>
                    teams = bodyResponse!!.teams

                    callback.onLoaded(teams)
                } else {
                    callback.onFailed(response.message())
                }
            }

        })
    }

    override fun loadTeam(teamId: String, callback: FootballDataSource.LoadTeamCallback) {
        val call = footballApi.getTeam(teamId)
        call.enqueue(object : Callback<TeamResponse>{
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                callback.onFailed(t.localizedMessage)
            }
            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                if (response.isSuccessful) {
                    val bodyResponse = response.body()
                    val teams: List<Team>
                    val team: Team
                    teams = bodyResponse!!.teams
                    team = teams.get(0)
                    callback.onLoaded(team)
                } else {
                    callback.onFailed(response.message())
                }
            }

        })
    }

    override fun setFavoriteTeam(favorite: Boolean, teamId: String, callback: FootballDataSource.SetFavoriteTeamCallback) {
        //maybe next store in rmeote server
    }
    override fun loadFavoriteLeague(callback: FootballDataSource.LoadFavoriteLeagueCallback) {
        //maybe next store in rmeote server
    }

    override fun setFavoriteLeague(leagueId: String, callback: FootballDataSource.SetFavoriteLeagueCallback) {
    }

    override fun loadEventLeague(isPastEvenet: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
        var call: Call<EventResponse>
        if(isPastEvenet) {
            call = footballApi.getEventsPast(leagueId)
        } else {
            call = footballApi.getEventsNext(leagueId)
        }
        call.enqueue(object : Callback<EventResponse>{
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                callback.onFailed(t.localizedMessage)
            }

            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val bodyResponse = response.body()
                    val events: List<Event>
                    events = bodyResponse!!.events

                    callback.onLoaded(events)
                } else {
                    callback.onFailed(response.message())
                }
            }

        })
    }
    override fun setFavoriteEvent(favorite: Boolean, eventId: String, callback: FootballDataSource.SetFavoriteEventCallback) {
    }
    override fun loadFavoriteEvent(callback: FootballDataSource.LoadFavEventLeagueCallback) {
    }
    override fun isEventHasFavorited(eventId: String, callback: FootballDataSource.IsEventFavLeagueCallback) {
    }
}