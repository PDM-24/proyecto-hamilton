package com.rostorga.calendariumv2.api

import com.rostorga.calendariumv2.api.Constants
import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.api.apiObject.UserApiObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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
    @POST(value = Constants.API_PATH + Constants.TASK_PATH + "/")
     fun postTask(@Body task: TaskApiObject): Call<ResponseBody>

    //Get Tasks From User At day
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.TASK_PATH + "/byuser" )
    fun getTasksAtDate(
        @Query("id") id : String,
        @Query("Day") day : Int,
        @Query("Month") month : Int,
        @Query("Year") year : Int
    ): Call<ResponseBody>

    //Get Tasks From User At Month and year

    //Get all Tasks From User

    //Delete Task
}