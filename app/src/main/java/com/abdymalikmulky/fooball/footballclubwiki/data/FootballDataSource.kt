package com.abdymalikmulky.fooball.footballclubwiki.data

import android.support.annotation.NonNull
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.league.League
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team


interface FootballDataSource {

    fun loadLeague(callback: LoadLeagueCallback)

    fun loadTeamLeague(@NonNull leagueId: String, @NonNull callback: LoadTeamsCallback)

    fun loadTeam(teamId: String, callback: LoadTeamCallback)

    fun setFavoriteTeam(favorite: Boolean, teamId: String, callback: SetFavoriteTeamCallback)

    fun setFavoriteLeague(leagueId: String, callback: SetFavoriteLeagueCallback)

    fun loadEventLeague(@NonNull isPastEvenet: Boolean, @NonNull leagueId: String,@NonNull  callback: LoadEventLeagueCallback)

    fun setFavoriteEvent(favorite: Boolean, eventId: String, callback: SetFavoriteEventCallback)

    fun loadFavoriteEvent(callback: LoadFavEventLeagueCallback)

    fun isEventHasFavorited(eventId: String, callback: IsEventFavLeagueCallback)

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

    interface SetFavoriteTeamCallback {
        fun onSavedTeam(teamId: String)
        fun onFailed(errorMsg: String)
    }

    interface IsTeamExistCallback {
        fun onTeamExisted()
        fun onTeamNotExisted()
    }

    interface SetFavoriteLeagueCallback {
        fun onSet(leagueId: String)
        fun onFailed(errorMsg: String)
    }

    interface LoadEventLeagueCallback {
        fun onLoaded(events: List<Event>)
        fun onFailed(errorMsg: String)
    }

    interface LoadFavEventLeagueCallback {
        fun onLoaded(eventId: ArrayList<String>)
        fun onFailed(errorMsg: String)
    }

    interface SetFavoriteEventCallback {
        fun onSavedEvent(eventId: String)
        fun onFailed(errorMsg: String)
    }

    interface IsEventFavLeagueCallback {
        fun onFavorited(isFav: Boolean)
        fun onFailed(errorMsg: String)
    }

}