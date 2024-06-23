package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class TeamApiObject(

    @SerializedName(value="TeamName")
    @Expose
    val name: String,

    @SerializedName(value="TeamCode")
    @Expose
    val code: String,

    @SerializedName(value="Leader")
    @Expose
    val leader: String,

)



