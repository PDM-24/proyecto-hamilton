package com.rostorga.calendariumv2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rostorga.calendariumv2.api.ApiClient
import com.rostorga.calendariumv2.api.apiObject.TaskApiObject
import com.rostorga.calendariumv2.data.database.AppDataBase
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData
import com.rostorga.calendariumv2.data.database.entities.UserWithTeams
import com.rostorga.calendariumv2.data.database.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val currentUserId = MutableLiveData<String?>()

    fun setUserLoggedIn(userId: String) {
        currentUserId.value = userId
    }

    fun logUserOut() {
        currentUserId.value = null
    }

    val getAllData: LiveData<List<UserData>>
    private val repository: UserRepo
    private val _usersWithTeams = MutableLiveData<List<UserWithTeams>>()
    val allTasks: LiveData<List<TaskData>>

    val usersWithTeams: LiveData<List<UserWithTeams>> get() = _usersWithTeams

    private val _TasksFromTeams = MutableLiveData<List<TaskData>>()
    val TaskFromTeams:  MutableLiveData<List<TaskData>> get() = _TasksFromTeams

    init {
        val userDao = AppDataBase.getDataBaseInstance(application).UserDao()
        val teamDao = AppDataBase.getDataBaseInstance(application).teamDao()
        val taskDao = AppDataBase.getDataBaseInstance(application).TaskDao()
        repository = UserRepo(userDao, teamDao, taskDao)
        getAllData = repository.getAllData
        allTasks = repository.allTasks // Initialize allTasks here
    }

    fun addUser(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addTeam(team: TeamData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTeam(team)
        }
    }

    fun addTask(task: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTasks(task)
        }
    }

    fun fetchUsersWithTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val usersWithTeams = repository.getUsersWithTeams()
            withContext(Dispatchers.Main) {
                _usersWithTeams.value = usersWithTeams
            }
        }
    }

    fun joinTeam(userId: Int, teamCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val team = repository.getTeamByCode(teamCode)
                if (team != null) {
                    val updatedTeam = team.copy(PersonId = userId)
                    repository.addTeam(updatedTeam)
                    Log.d("UserViewModel", "User joined team successfully: $updatedTeam")
                } else {
                    Log.d("UserViewModel", "Team not found with code: $teamCode")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error joining team", e)
            }
        }
    }


    val gson = GsonBuilder().create()
    fun getRemoteTeamTasks(id: String) {
        val call = ApiClient.apiService.getTasksForTeam(id)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val json = JSONObject(it.string())
                    val body = json.getString("data")

                    val typeToken = object : TypeToken<List<TaskApiObject>>() {}.type
                    val taskObject = gson.fromJson<List<TaskApiObject>>(body, typeToken)
                    val localTaskList: MutableList<TaskData> = mutableListOf()

                    Log.i("apiviewmodel", taskObject.toString())

                    taskObject.forEach { item ->
                        val temp = convertTaskAPi(item)
                        localTaskList.add(temp)
                     }

                    TaskFromTeams.postValue(localTaskList.toList())


                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("apiviewmodel", t.message.toString())
            }
        })

    }

    fun convertTaskAPi(temp: TaskApiObject): TaskData{
        return TaskData(TaskName = temp.name, TaskDesc = temp.description, Date = temp.date, TimeStart =  temp.timeStart,
           TimeFinish =  temp.timeEnd, PersonId =  1)
    }
}
