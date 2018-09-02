package com.abdymalikmulky.fooball.footballclubwiki.data.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Team (
        @SerializedName("idTeam")
        var teamId: String,

        @SerializedName("strTeam")
        var teamName: String?,

        @SerializedName("strDescriptionEN")
        var teamDescription: String?,

        @SerializedName("strTeamBadge")
        var teamBadge: String) : Serializable {

        constructor() : this("", "", "", "")


        companion object {
                const val TABLE: String = "TEAM"
                const val ID: String = "ID_"
                const val TEAM_ID: String = "TEAM_ID"
                const val LEAGUE_ID: String = "LEAGUE_ID"
                const val TEAM_NAME: String = "TEAM_NAME"
                const val TEAM_BADGE: String = "TEAM_BADGE"
                const val IS_FAVORITE: String = "IS_FAVORITE"
        }
}