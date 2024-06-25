package com.rostorga.calendariumv2.api

import com.google.gson.annotations.SerializedName
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

    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.USER_PATH + "/login")
    fun loginUser(@Body credentials: UserLogin): Call<ResponseBody>

    //Post User
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.USER_PATH + "/register")
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

    //join team
    @Headers(value= ["Content-Type: application/json"])
    @POST(value= Constants.API_PATH + Constants.TEAM_PATH+"/joinByCode")
    fun joinTeamByCode(@Body joinRequest: JoinTeamRequest): Call<ResponseBody>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.TEAM_PATH + "/{id}")
    fun getTeam(@Path("id") id: String): Call<ResponseBody>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.TEAM_PATH + "byteam/{id}")
    fun getTasksForTeam(@Path("id") id: String): Call<ResponseBody>


    @GET("generateTeamCode")
    fun generateTeamCode(): Call<TeamResponse>


    data class TeamResponse(val message: String, val data: TeamData) {
    }


    data class TeamData(val teamName: String, val teamCode: String, val leaderId: String)


    data class JoinTeamRequest(
        @SerializedName("userId")
        val userId: String,
        @SerializedName("teamCode")
        val teamCode: String
    )

}