package com.rostorga.calendariumv2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rostorga.calendariumv2.data.database.AppDataBase
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData
import com.rostorga.calendariumv2.data.database.entities.UserWithTeams
import com.rostorga.calendariumv2.data.database.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application): AndroidViewModel( application){

    val getAllData: LiveData<List<UserData>>
    private val repository: UserRepo
    private val _usersWithTeams = MutableLiveData<List<UserWithTeams>>()
    val usersWithTeams: LiveData<List<UserWithTeams>> get() = _usersWithTeams

    init {
        val userDao = AppDataBase.getDataBaseInstance(application).UserDao()
        val teamDao = AppDataBase.getDataBaseInstance(application).teamDao()
        val taskDao = AppDataBase.getDataBaseInstance(application).TaskDao()
        repository=UserRepo(userDao, teamDao, taskDao)
        getAllData = repository.getAllData
    }

    fun addUser(user: UserData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addTeam(team: TeamData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTeam(team)
        }
    }

    fun addTask(task: TaskData){
        viewModelScope.launch(Dispatchers.IO){
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
}
