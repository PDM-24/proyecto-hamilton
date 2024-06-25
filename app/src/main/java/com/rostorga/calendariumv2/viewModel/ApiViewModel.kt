package com.rostorga.calendariumv2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rostorga.calendariumv2.api.ApiClient
import com.rostorga.calendariumv2.api.apiObject.UserApiObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rostorga.calendariumv2.api.ApiService
import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.api.apiObject.TaskDurationObject
import com.rostorga.calendariumv2.api.apiObject.TeamApiObject
import com.rostorga.calendariumv2.api.apiObject.UserLogin
import com.rostorga.calendariumv2.api.apiObject.UserNameApiObject
import com.rostorga.calendariumv2.objects.UserManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ApiViewModel : ViewModel() {

    val gson = GsonBuilder().create()

    private val _taskList = MutableLiveData<List<TaskApiObject>>()
    val taskList: LiveData<List<TaskApiObject>> get() = _taskList

    val loginResponse = MutableLiveData<String>()
    val currentUserId = MutableLiveData<String>()

    private val _registeredUserId = MutableLiveData<String?>()
    val registeredUserId: MutableLiveData<String?> get() = _registeredUserId

    //this is to retrieve the id
    fun registerUser(user: UserApiObject) {
        val call = ApiClient.apiService.postUser(user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val userObject = convertUser(it)
                        _registeredUserId.postValue(userObject.id)
                        Log.d("ApiViewModel", "Registered User ID: ${userObject.id}")
                    }
                } else {
                    Log.e("ApiViewModel", "Failed to register user: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiViewModel", "Error registering user", t)
            }
        })
    }

    //Converts task json object from api response to our custom TaskApiObject
    fun convertTask(response: ResponseBody): TaskApiObject {
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

    //Converts user json object from api response to our custom UserApiObject
    fun convertUser(response: ResponseBody): UserApiObject {
        val json = JSONObject(response.string())
        val body = json.getString("data")
        val name = JSONObject(body).getString("Name")

        val userObject = gson.fromJson(body, UserApiObject::class.java)
        userObject.Name = gson.fromJson(name, UserNameApiObject::class.java)

        Log.i("apiviewmodel", body)
        Log.i("apiviewmodel", name)
        Log.i("apiviewmodel", userObject.toString())
        Log.i("apiviewmodel", userObject.id.toString())

        return userObject
    }

    fun convertTeam(response: ResponseBody): TeamApiObject {
        val json = JSONObject(response.string())
        val body = json.getString("data")

        val teamObject = gson.fromJson(body, TeamApiObject::class.java)

        Log.i("apiviewmodel", body)
        Log.i("apiviewmodel", teamObject.toString())

        return teamObject
    }

    fun getUser(id: String = "66707ac4cd39d615e5addaee"): UserApiObject {
        val call = ApiClient.apiService.getUser(id)
        var returnUser: UserApiObject = UserApiObject(
            UserNameApiObject("request", "failed"),
            "failure", "", ""
        )

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

    //this registers the user
    fun postUser(requestData: UserApiObject): UserApiObject {
        val call = ApiClient.apiService.postUser(requestData)
        var returnUser: UserApiObject = UserApiObject(
            UserNameApiObject("", ""), "", "", "")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    returnUser = convertUser(it)
                    _registeredUserId.value = returnUser.id
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }
        })

        return returnUser
    }

    fun postTeam(requestData: TeamApiObject): MutableLiveData<TeamApiObject?> {
        val result = MutableLiveData<TeamApiObject?>()
        val call = ApiClient.apiService.postTeam(requestData)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    result.value = convertTeam(it)
                } ?: run {
                    result.value = null
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiViewModel", "Error posting team", t)
                result.value = null
            }
        })

        return result
    }

    fun postTask(requestData: TaskApiObject, teamId: String): TaskApiObject {
        requestData.team = teamId
        val call = ApiClient.apiService.postTask(requestData)
        var returnTask: TaskApiObject = TaskApiObject(
            "", "", 1, 1, 1,
            TaskDurationObject(1223, 1440), ""
        )

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

    fun getTasksAtDate(id: String, day: Int, month: Int, year: Int, teamId: String) {
        val call = ApiClient.apiService.getTasksAtDate(id, day, month, year, teamId)

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

    fun getTasksFromUser(id: String, teamId: String) {
        val call = ApiClient.apiService.getTasksFromUser(id, teamId)

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


    fun loginUser(username: String, password: String) {
        val userLogin = UserLogin(username, password)
        val call = ApiClient.apiService.loginUser(userLogin)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        try {
                            val jsonResponse = JSONObject(responseBody.string())
                            val dataArray = jsonResponse.getJSONArray("data")
                            if (dataArray.length() > 0) {
                                val userData = dataArray.getJSONObject(0)
                                val userId = userData.optString("_id", "N/A")
                                if (userId != "N/A") {
                                    currentUserId.postValue(userId)
                                    UserManager.setUser(userId)
                                    //loginResponse.postValue("Login successful! User ID: $userId")
                                    loginResponse.postValue("Login successful!")
                                    Log.d("ApiViewModel", "Logged in User ID: $userId")

                                } else {
                                    loginResponse.postValue("Login failed: User ID not found in response")
                                    Log.e("ApiViewModel", "User ID not found in JSON response")
                                }
                            } else {
                                loginResponse.postValue("Login failed: No user data found")
                                Log.e("ApiViewModel", "No user data found in JSON response")
                            }
                        } catch (e: Exception) {
                            loginResponse.postValue("Login failed: Error parsing response")
                            Log.e("ApiViewModel", "Error parsing JSON response", e)
                        }
                    } ?: run {
                        loginResponse.postValue("Login failed: Empty response")
                        Log.e("ApiViewModel", "Received empty response body")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    loginResponse.postValue("Login failed: $errorBody")
                    Log.e("ApiViewModel", "Login failed with HTTP error: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginResponse.postValue("Login failed, check your network connection!")
                Log.e("ApiViewModel", "Network call failed", t)
            }
        })
    }
    //join team
    fun apiJoinTeam(userId: String, teamCode: String) {
        val joinRequest = ApiService.JoinTeamRequest(userId, teamCode)
        val call = ApiClient.apiService.joinTeamByCode(joinRequest)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("jointeam", response.body().toString())

                    // Handle success, maybe update UI or state
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Failed to join team"
                    Log.d("jointeam", errorMessage)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network errors or other unexpected errors
            }
        })
    }


}




