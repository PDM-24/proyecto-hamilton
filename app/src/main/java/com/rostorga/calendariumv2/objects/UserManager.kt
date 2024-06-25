package com.rostorga.calendariumv2.objects


object UserManager{
    var userId: String? = null

    fun setUser(id: String){
        userId = id

    }

    fun getUser(): String?{
        return userId
    }

}