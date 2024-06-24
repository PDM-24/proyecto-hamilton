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
import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.api.apiObject.TaskDurationObject
import com.rostorga.calendariumv2.api.apiObject.TeamApiObject
import com.rostorga.calendariumv2.api.apiObject.UserNameApiObject
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ApiViewModel : ViewModel() {

    val gson = GsonBuilder().create()

    private val _taskList = MutableLiveData<List<TaskApiObject>>()
    val taskList: LiveData<List<TaskApiObject>> get() = _taskList

    private val _currentUserId = MutableLiveData<String>()
    val currentUserId: LiveData<String> get() = _currentUserId

    //this is for the login
    private val _loginResponse = MutableLiveData<String>()
    val loginResponse: LiveData<String> get() = _loginResponse

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
            UserNameApiObject("", ""),
            "", "", "")

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
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()
        val request = Request.Builder()
            .url("https://58e2f7e2-3995-4a9a-ab9e-8087359857a9.mock.pstmn.io/user/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                _loginResponse.postValue("Login failed, check your username and password!")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let {
                        Log.d("ApiViewModel", "Response JSON: $it")  // Log the entire JSON response

                        try {
                            val json = JSONObject(it)
                            val token = json.optString("token", null)

                            if (token != null) {
                                Log.d("ApiViewModel", "Token: $token")

                                // Try to get user info, assuming different structure
                                val userInfo = json.optJSONObject("user") ?: json
                                val userId = userInfo.optString("_id", "N/A")
                                val userFirstName = userInfo.optJSONObject("Name")?.optString("first", "N/A") ?: "N/A"
                                val userLastName = userInfo.optJSONObject("Name")?.optString("last", "N/A") ?: "N/A"
                                val userName = userInfo.optString("UserName", "N/A")

                                _currentUserId.postValue(userId)
                                _loginResponse.postValue("Login successful!")

                                // Log user details
                                Log.d("ApiViewModel", "User ID: $userId")
                                Log.d("ApiViewModel", "User First Name: $userFirstName")
                                Log.d("ApiViewModel", "User Last Name: $userLastName")
                                Log.d("ApiViewModel", "User Name: $userName")
                            } else {
                                _loginResponse.postValue("Login failed: token missing in response")
                            }
                        } catch (e: Exception) {
                            Log.e("ApiViewModel", "Error parsing JSON response", e)
                            _loginResponse.postValue("Login failed: error parsing response")
                        }
                    }
                } else {
                    _loginResponse.postValue("Login failed")
                }
            }
        })
    }
}
