package com.rostorga.calendariumv2.api

import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.api.apiObject.TeamApiObject
import com.rostorga.calendariumv2.api.apiObject.UserApiObject
import com.rostorga.calendariumv2.api.apiObject.UserLogin
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("http://localhost:3000/api/user/login")
    fun loginUser(@Body credentials: UserLogin): Call<ResponseBody>

    //Post User
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.USER_PATH + '/')
    fun postUser(@Body user: UserApiObject): Call<ResponseBody>

    //Get User
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.USER_PATH + "/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseBody>

    //Post Task
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.TASK_PATH + '/')
     fun postTask(@Body task: TaskApiObject): Call<ResponseBody>

    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.TEAM_PATH + '/')
    fun postTeam(@Body team: TeamApiObject): Call<ResponseBody>

    //Get Tasks From User At day
     // To get tasks from User At certain month and year simply set argument day to 0
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.TASK_PATH + "/byuser" + "/{id}" + "/{day}-{month}-{year}")
    fun getTasksAtDate(
        @Path("id") id: String,
        @Path("day") day: Int,
        @Path("month") month: Int,
        @Path("year") year: Int,
        teamId: String
    ): Call<ResponseBody>

    //Get all Tasks From User

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.TASK_PATH + "/byuser" + "/{id}" )
    fun getTasksFromUser(
        @Path("id") id: String,
        teamId: String,
    ): Call<ResponseBody>

    //Delete Task
}