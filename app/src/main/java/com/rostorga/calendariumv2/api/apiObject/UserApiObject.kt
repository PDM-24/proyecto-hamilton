package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserApiObject(
    @SerializedName("Name")
    @Expose
    var Name: UserNameApiObject,
    @SerializedName("UserName")
    @Expose
    val UserName: String,
    @SerializedName("Password")
    @Expose
    val Password: String,
    @SerializedName("IsLeader")
    @Expose
    val isLeader: Boolean,
    @SerializedName("_id")
    @Expose
    val id: String? = null
)

data class UserNameApiObject(
    @SerializedName("first")
    @Expose
    val First: String,
    @SerializedName("last")
    @Expose
    val Last: String,
)