package com.abdymalikmulky.fooball.footballclubwiki.data.league

import com.google.gson.annotations.SerializedName

data class League (
        @SerializedName("idLeague")
        val leagueId: String,

        @SerializedName("strLeague")
        val leagueName: String,

        @SerializedName("strSport")
        val sportType: String)