package com.abdymalikmulky.fooball.footballclubwiki.data.player

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Player (
        @SerializedName("idPlayer")
        var idPlayer: String,

         @SerializedName("strPlayer")
        var name: String,

        @SerializedName("strPosition")
        var position: String?,

        @SerializedName("dateBorn")
        var dateBorn: String?,

        @SerializedName("strBirthLocation")
        var dateLocation: String?,

        @SerializedName("dateSigned")
        var dateSigned: String?,

        @SerializedName("strCutout")
        var photo: String?,

        @SerializedName("strNationality")
        var nationality: String?,

        @SerializedName("strFanart1")
        var banner: String?,

        @SerializedName("strDescriptionEN")
        var description: String?) : Serializable