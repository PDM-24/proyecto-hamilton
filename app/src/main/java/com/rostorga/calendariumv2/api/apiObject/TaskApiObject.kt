package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TaskApiObject(

    @SerializedName(value = "Name")
    @Expose
    val name: String,

    @SerializedName(value = "Description")
    val description: String,

    @SerializedName(value = "Date")
    @Expose
    val date: String,

    @SerializedName(value = "TimeStart")
    @Expose
    val timeStart: String,

    @SerializedName(value = "TimeEnd")
    @Expose
    val timeEnd: String,

    @SerializedName(value = "UserRef")
    @Expose
    val userRef: String,

    @SerializedName(value = "TeamRef")
    @Expose
    var team: String? = null,

    @SerializedName(value = "_id")
    @Expose
    val id: String? = null
)

data class TaskDurationObject(
    @SerializedName(value = "Start")
    @Expose
    val start: Int,

    @SerializedName(value = "End")
    @Expose
    val end: Int
)

