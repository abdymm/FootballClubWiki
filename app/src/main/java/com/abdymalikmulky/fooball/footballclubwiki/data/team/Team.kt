package com.abdymalikmulky.fooball.footballclubwiki.data.team

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Team (
        @SerializedName("strTeam")
        val teamName: String?,

        @SerializedName("strDescriptionEN")
        val teamDescription: String?,

        @SerializedName("strTeamBadge")
        val teamBadge: String?) : Serializable