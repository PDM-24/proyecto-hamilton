package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class userApiObject(
    @SerializedName(value = "_id")
    @Expose
    val id: String? = null,
    @SerializedName(value = "Name")
    @Expose
    val name: userNameApiObject,
    @SerializedName(value = "UserName")
    val userName: String,
    @SerializedName(value = "Password")
    @Expose
    val password: String
)

userNameApiObject(
    @SerializedName(value = 'first')
    @Expose
    val frist: String,
    @SerializedName(value = 'last')
    @Expose
    val last: String,
)