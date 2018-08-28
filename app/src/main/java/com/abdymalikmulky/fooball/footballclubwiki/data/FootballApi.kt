package com.abdymalikmulky.fooball.footballclubwiki.data

import com.abdymalikmulky.fooball.footballclubwiki.data.event.EventResponse
import com.abdymalikmulky.fooball.footballclubwiki.data.league.LeagueResponse
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.data.team.TeamResponse
import id.or.ypt.agendaapp.util.EndpointUtil
import retrofit2.Call
import retrofit2.http.*


interface FootballApi {

    @GET(EndpointUtil.GET_LEAGUES)
    fun getLeagues(): Call<LeagueResponse>

    @GET(EndpointUtil.GET_TEAMS)
    fun getTeams(@Query(EndpointUtil.KEY_ID) leagueId: String): Call<TeamResponse>

    @GET(EndpointUtil.GET_TEAM)
    fun getTeam(@Query(EndpointUtil.KEY_ID) teamId: String): Call<TeamResponse>

    @GET(EndpointUtil.GET_PLAYERS)
    fun getPlayers(@Query(EndpointUtil.KEY_ID) teamId: String): Call<Team>

    @GET(EndpointUtil.GET_EVENT_PAST)
    fun getEventsPast(@Query(EndpointUtil.KEY_ID) leagueId: String): Call<EventResponse>

    @GET(EndpointUtil.GET_EVENT_NEXT)
    fun getEventsNext(@Query(EndpointUtil.KEY_ID) leagueId: String): Call<EventResponse>




}