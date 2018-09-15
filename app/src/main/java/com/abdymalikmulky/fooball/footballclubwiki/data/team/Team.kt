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
        var teamBadge: String,

        @SerializedName("strAlternate")
        var teamNick: String?,

        @SerializedName("intFormedYear")
        var formedYear: String?,

        @SerializedName("strCountry")
        var teamCountry: String?,

        @SerializedName("intStadiumCapacity")
        var stadiumCapacity: String?,

        @SerializedName("strStadium")
        var stadiumName: String?,

        @SerializedName("strStadiumLocation")
        var stadiumLocation: String?,

        @SerializedName("strStadiumDescription")
        var stadiumDesc: String?,

        @SerializedName("strStadiumThumb")
        var stadiumThumb: String?,

        @SerializedName("strTeamBanner")
        var banner: String?,

        @SerializedName("strTwitter")
        var twitter: String?,

        @SerializedName("strWebsite")
        var website: String?,

        @SerializedName("strYoutube")
        var youtube: String?) : Serializable {

        constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        constructor(teamId: String, teamName: String, teamDescription: String?, teamBadge: String) : this() {
                this.teamId = teamId
                this.teamName = teamName
                this.teamDescription = teamDescription
                this.teamBadge = teamBadge
        }

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