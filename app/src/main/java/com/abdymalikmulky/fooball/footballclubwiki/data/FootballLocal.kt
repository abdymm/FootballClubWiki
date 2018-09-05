package com.abdymalikmulky.fooball.footballclubwiki.data


import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.abdymalikmulky.fooball.footballclubwiki.R
import com.abdymalikmulky.fooball.footballclubwiki.data.event.Event
import com.abdymalikmulky.fooball.footballclubwiki.data.team.Team
import com.abdymalikmulky.fooball.footballclubwiki.util.SharedPreferenceUtil
import org.jetbrains.anko.db.*

class FootballLocal(context: Context) : FootballDataSource {

    internal var sharedPreferenceUtil: SharedPreferenceUtil

    internal var context: Context

    internal var database: DBHelper


    init {
        this.context = context
        this.database = DBHelper(context)
        this.sharedPreferenceUtil = SharedPreferenceUtil(context)
    }

    override fun loadLeague(callback: FootballDataSource.LoadLeagueCallback) {
        //local db
    }

    override fun loadTeamLeague(leagueId: String, callback: FootballDataSource.LoadTeamsCallback) {

    }

    override fun loadEventLeague(isPastEvent: Boolean, leagueId: String, callback: FootballDataSource.LoadEventLeagueCallback) {
    }

    override fun setFavoriteLeague(leagueId: String, callback: FootballDataSource.SetFavoriteLeagueCallback) {
        sharedPreferenceUtil.editor.putString(context.getString(R.string.PREF_LEAGUE), leagueId)
        sharedPreferenceUtil.editor.commit()
        callback.onSet(leagueId)
    }

    override fun loadTeam(teamId: String, callback: FootballDataSource.LoadTeamCallback) {
        var team = getTeam(teamId)
        callback.onLoaded(team)
    }

    fun getTeam(teamId: String): Team {
        var team = Team()
        database.use {
            select(Team.TABLE)
                    .whereArgs("(${Team.TEAM_ID} = {teamId})",
                            "teamId" to teamId).exec {

                        if(this.moveToFirst())
                            team = Team(this.getString(1), this.getString(3), "", this.getString(4))
                    }
        }
        return team
    }

    fun saveTeam(team: Team, leagueId: String): Boolean {
        try {
            database.use {
                insert(Team.TABLE,
                        Team.TEAM_ID to team.teamId,
                        Team.LEAGUE_ID to leagueId,
                        Team.TEAM_NAME to team.teamName,
                        Team.TEAM_BADGE to team.teamBadge,
                        Team.IS_FAVORITE to 0)
            }
            return true
        } catch (e: SQLiteConstraintException) {
            return false
        }
    }

    fun isTeamExist(teamId: String, callback: FootballDataSource.IsTeamExistCallback) {
        database.use {
            select(Team.TABLE)
                    .whereArgs("(${Team.TEAM_ID} = {teamId})",
                            "teamId" to teamId).exec {
                        if(this.count > 0) {
                            callback.onTeamExisted()
                        } else {
                            callback.onTeamNotExisted()
                        }
                    }
        }
    }

    override fun setFavoriteTeam(favorite: Boolean, teamId: String, callback: FootballDataSource.SetFavoriteTeamCallback) {

        try {
            database.use {
                update(Team.TABLE, Team.IS_FAVORITE to favorite)
                        .whereArgs("${Team.TEAM_ID} = {teamId}", "teamId" to teamId)
                        .exec()
            }
            callback.onSavedTeam(teamId)
        } catch (e: Exception) {
            callback.onFailed(e.localizedMessage)
        }
    }

    override fun setFavoriteEvent(favorite: Boolean, eventId: String, callback: FootballDataSource.SetFavoriteEventCallback) {
        if(favorite) {
            try {
                database.use {
                    insert(Event.TABLE,
                            Event.EVENT_ID to eventId)
                }
                callback.onSavedEvent(eventId)
            } catch (e: SQLiteConstraintException) {
                callback.onFailed(e.localizedMessage)
            }
        } else {
            try {
                database.use {
                    delete(Event.TABLE, "(${Event.EVENT_ID} = {id})",
                            "id" to eventId)
                }
                callback.onSavedEvent(eventId)
            } catch (e: Exception) {
                callback.onFailed(e.localizedMessage)
            }

        }
    }

    override fun loadFavoriteEvent(callback: FootballDataSource.LoadFavEventLeagueCallback) {
        var eventIds = ArrayList<String>()
        database.use {
            select(Event.TABLE).exec {
                if (moveToFirst()) {
                    do {
                        eventIds.add(this.getString(1))
                    } while (moveToNext())
                }
                callback.onLoaded(eventIds)
            }
        }
    }

    override fun isEventHasFavorited(eventId: String, callback: FootballDataSource.IsEventFavLeagueCallback) {
        database.use {
            select(Event.TABLE)
                .whereArgs("(${Event.EVENT_ID} = {id})",
                "id" to eventId).exec {
                if(this.count > 0) {
                    callback.onFavorited(true)
                } else {
                    callback.onFavorited(false)
                }
            }
        }
    }
}