package com.rostorga.calendariumv2.api.apiObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class taskApiObject(
    @SerializedName(value = "_id")
    @Expose
    val id: String? = null,
    
    @SerializedName(value = "Name")
    @Expose
    val name: String,
    
    @SerializedName(value = "Description")
    val description: String,
    
    @SerializedName(value = "Day")
    @Expose
    val day: Int
    
    @SerializedName(value = "Month")
    @Expose
    val month: Int
    
    @SerializedName(value = "Year")
    @Expose
    val year: Int

    @SerializedName(value = "Time")
    @Expose
    val time: taskDurationObject

    @SerializedName(value = "UserRef")
    @Expose
    val userRef: String

    @SerializedName(value = "TeamRef")
    @Expose
    val team: String? = null
)

data class taskDurationObject(
    @SerializedName(value = "Start")
    @Expose 
    val start: Int

    @SerializedName(value = "End")
    @Expose
    val end: Int
)

