package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TaskApiObject(

    @SerializedName(value = "Name")
    @Expose
    val name: String,

    @SerializedName(value = "Description")
    val description: String,

    @SerializedName(value = "Day")
    @Expose
    val day: Int,

    @SerializedName(value = "Month")
    @Expose
    val month: Int,

    @SerializedName(value = "Year")
    @Expose
    val year: Int,

    @SerializedName(value = "Time")
    @Expose
    var time: TaskDurationObject,

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

