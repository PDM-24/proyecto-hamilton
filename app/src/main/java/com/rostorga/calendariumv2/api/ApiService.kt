package com.rostorga.calendariumv2.api

import com.rostorga.calendariumv2.api.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    //Post User
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.USER_PATH + '/')
    fun insertUser(@Body user: FamiliaApiObject): Call<ResponseBody>

    //Get User
    @Headers(value = ["Content-Type: application/json"])
    @GET(value = Constants.API_PATH + Constants.USER_PATH + '/:id')
    fun insertFamilia(@Body familia: FamiliaApiObject): Call<ResponseBody>

    //Post Task
    @Headers(value = ["Content-Type: application/json"])
    @POST(value = Constants.API_PATH + Constants.TASK_PATH + '/')
     fun insertPersona(@Body persona: PersonaApiObject): Call<ResponseBody>


    //Get Tasks From User At day

    //Get Tasks From User At Month and year

    //Get all Tasks From User

    //Delete Task
}