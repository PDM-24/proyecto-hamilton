package com.rostorga.calendariumv2.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rostorga.calendariumv2.api.ApiClient
import com.rostorga.calendariumv2.api.apiObject.UserApiObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody
import com.google.gson.GsonBuilder
import com.rostorga.calendariumv2.api.apiObject.UserNameApiObject
import org.json.JSONObject

class ApiViewModel : ViewModel() {
    val gson = GsonBuilder().create()
    fun getUser(id: String = "66707ac4cd39d615e5addaee") {
        val call = ApiClient.apiService.getUser(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val json = JSONObject(it.string())
                    val body = json.getString("data")
                    val name = JSONObject(body).getString("Name")

                    val userObject = gson.fromJson(body, UserApiObject::class.java)
                    userObject.Name = gson.fromJson(name, UserNameApiObject::class.java)

                    Log.i("apiviewmodel", body)
                    Log.i("apiviewmodel", name)
                    Log.i("apiviewmodel", userObject.toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }
        })
    }


    fun postUser(requestData: UserApiObject): UserApiObject {
        val call = ApiClient.apiService.postUser(requestData)
        var returnUser: UserApiObject = UserApiObject(UserNameApiObject("", ""),
            "", "", false, "")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val json = JSONObject(it.string())
                    val body = json.getString("data")
                    val name = JSONObject(body).getString("Name")

                    val userObject = gson.fromJson(body, UserApiObject::class.java)
                    userObject.Name = gson.fromJson(name, UserNameApiObject::class.java)

                    Log.i("apiviewmodel", body)
                    Log.i("apiviewmodel", name)
                    Log.i("apiviewmodel", userObject.toString())

                    returnUser = userObject

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }

        })

        return returnUser
    }
}
