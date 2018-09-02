package com.abdymalikmulky.fooball.footballclubwiki.data.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Event(
        @SerializedName("idEvent")
        val idEvent: String,

        @SerializedName("strDate")
        val dateEvent: String,

        @SerializedName("strTime")
        val timeEvent: String,

        @SerializedName("idHomeTeam")
        val idHomeTeam: String,

        @SerializedName("idAwayTeam")
        val idAwayTeam: String,

        @SerializedName("strHomeTeam")
        val homeTeam: String,

        @SerializedName("strAwayTeam")
        val awayTeam: String,

        var homeTeamBadge: String,

        var awayTeamBadge: String,

        @SerializedName("intHomeScore")
        val homeScore: Int,

        @SerializedName("intAwayScore")
        val awayScore: Int,

        @SerializedName("strHomeGoalDetails")
        val homeGoalDetails: String?,

        @SerializedName("strHomeLineupGoalkeeper")
        val homeLineUpGK: String,

        @SerializedName("strHomeLineupDefense")
        val homeLineUpDEF: String,

        @SerializedName("strHomeLineupMidfield")
        val homeLineUpMD: String,

        @SerializedName("strHomeLineupForward")
        val homeLineUpFW: String,

        @SerializedName("strHomeLineupSubstitutes")
        val homeLineUpSUB: String,

        @SerializedName("strAwayGoalDetails")
        val awayGoalDetails: String?,

        @SerializedName("strAwayLineupGoalkeeper")
        val awayLineUpGK: String,

        @SerializedName("strAwayLineupDefense")
        val awayLineUpDEF: String,

        @SerializedName("strAwayLineupMidfield")
        val awayLineUpMD: String,

        @SerializedName("strAwayLineupForward")
        val awayLineUpFW: String,

        @SerializedName("strAwayLineupSubstitutes")
        val awayLineUpSUB: String) : Serializable {

        companion object {
                const val TABLE: String = "EVENT_FAV"
                const val ID: String = "ID_"
                const val EVENT_ID: String = "EVENT_ID"
        }
}
