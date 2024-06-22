package com.rostorga.calendariumv2.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.google.gson.reflect.TypeToken
import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.api.apiObject.TaskDurationObject
import com.rostorga.calendariumv2.api.apiObject.UserNameApiObject
import org.json.JSONObject

class ApiViewModel : ViewModel() {

    val gson = GsonBuilder().create()

    private val _taskList = MutableLiveData<List<TaskApiObject>>()
    val taskList: LiveData<List<TaskApiObject>> get() = _taskList

    //Converts task json object from api response to our custom TaskApiObject
    fun convertTask(response: ResponseBody): TaskApiObject{
        val json = JSONObject(response.string())
        val body = json.getString("data")
        val time = JSONObject(body).getString("Time")

        val taskObject = gson.fromJson(body, TaskApiObject::class.java)
        taskObject.time = gson.fromJson(time, TaskDurationObject::class.java)

        Log.i("apiviewmodel", body)
        Log.i("apiviewmodel", time)
        Log.i("apiviewmodel", taskObject.toString())

        return taskObject
    }

    //Converts user json object from api response to our custom UserapiObject
    fun convertUser(response: ResponseBody): UserApiObject{
        val json = JSONObject(response.string())
        val body = json.getString("data")
        val name = JSONObject(body).getString("Name")

        val userObject = gson.fromJson(body, UserApiObject::class.java)
        userObject.Name = gson.fromJson(name, UserNameApiObject::class.java)

        Log.i("apiviewmodel", body)
        Log.i("apiviewmodel", name)
        Log.i("apiviewmodel", userObject.toString())

        return userObject
    }
    fun getUser(id: String = "66707ac4cd39d615e5addaee") : UserApiObject {
        val call = ApiClient.apiService.getUser(id)
        var returnUser: UserApiObject = UserApiObject(UserNameApiObject("request", "failed"),
            "failure", "", false, "")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    returnUser = convertUser(it)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }
        })

        return returnUser
    }


    fun postUser(requestData: UserApiObject): UserApiObject {
        val call = ApiClient.apiService.postUser(requestData)
        var returnUser: UserApiObject = UserApiObject(UserNameApiObject("", ""),
            "", "", false, "")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    returnUser = convertUser(it)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }

        })

        return returnUser
    }

    fun postTask(requestData: TaskApiObject): TaskApiObject {
        val call = ApiClient.apiService.postTask(requestData)
        var returnTask: TaskApiObject = TaskApiObject("","",1,1,1,
            TaskDurationObject(1223,1440),"")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    returnTask = convertTask(it)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }

        })
        return returnTask
    }

    fun getTasksFromDate(id : String, day: Int, month: Int, year: Int){
        val call = ApiClient.apiService.getTasksAtDate(id, day, month, year)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val json = JSONObject(it.string())
                    val body = json.getString("data")

                    val typeToken = object : TypeToken<List<TaskApiObject>>() {}.type
                    val taskObject = gson.fromJson<List<TaskApiObject>>(body, typeToken)

                    Log.i("apiviewmodel", taskObject.toString())

                    _taskList.value = taskObject
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }

        })
    }

    fun getTasksFromUser(id : String){
        val call = ApiClient.apiService.getTasksFromUser(id)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val json = JSONObject(it.string())
                    val body = json.getString("data")

                    val typeToken = object : TypeToken<List<TaskApiObject>>() {}.type
                    val taskObject = gson.fromJson<List<TaskApiObject>>(body, typeToken)

                    Log.i("apiviewmodel", taskObject.toString())

                    _taskList.value = taskObject
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }

        })
    }
}
