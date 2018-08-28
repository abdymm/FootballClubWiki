package com.abdymalikmulky.fooball.footballclubwiki.data.event

import com.google.gson.annotations.SerializedName

data class Event(
        @SerializedName("idEveent")
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

        @SerializedName("intHomeScore")
        val homeScore: Int,

        @SerializedName("intAwayScore")
        val awayScore: Int,

        @SerializedName("strHomeGoalDetails")
        val homeGoalDetails: String,

        @SerializedName("strHomeLineupGoalkeeper")
        val homeLineUpGK: String,

        @SerializedName("strHomeLineupDefense")
        val homeLineUpDEF: String,

        @SerializedName("strHomeLineupMidfield")
        val homeLineUMD: String,

        @SerializedName("strHomeLineupForward")
        val homeLineUpFW: String,

        @SerializedName("strHomeLineupSubstitutes")
        val homeLineUpSUB: String,

        @SerializedName("strAwayGoalDetails")
        val awayGoalDetails: String,

        @SerializedName("strAwayLineupGoalkeeper")
        val awayLineUpGK: String,

        @SerializedName("strAwayLineupDefense")
        val awayLineUpDEF: String,

        @SerializedName("strAwayLineupMidfield")
        val awayLineUMD: String,

        @SerializedName("strAwayLineupForward")
        val awayLineUpFW: String,

        @SerializedName("strAwayLineupSubstitutes")
        val awayLineUpSUB: String)
