package com.rostorga.calendariumv2.objects

object TeamManager {

    var TeamCode: String? = null
    var Id: String? = null

    fun setTeam(code: String){
        TeamCode = code
    }

    fun getTeam():String?{
        return TeamCode
    }

    fun setTeamId(code: String){
        Id = code
    }

    fun getTeamId():String?{
        return Id
    }
}