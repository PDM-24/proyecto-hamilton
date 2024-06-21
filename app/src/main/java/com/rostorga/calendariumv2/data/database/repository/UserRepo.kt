package com.rostorga.calendariumv2.data.database.repository

import androidx.lifecycle.LiveData
import com.rostorga.calendariumv2.data.database.dao.TaskDao
import com.rostorga.calendariumv2.data.database.dao.TeamDao
import com.rostorga.calendariumv2.data.database.dao.UserDao
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData
import com.rostorga.calendariumv2.data.database.entities.UserWithTeams

class UserRepo(private val userDao: UserDao, private val teamDao:TeamDao, private val taskDao:TaskDao) {


    val getAllData = userDao.getAllUsers()

    suspend fun addUser(user: UserData) {
        userDao.addUser(user)
    }

    suspend fun addTeam(team: TeamData) {
        teamDao.addTeam(team)
    }

    suspend fun addTasks(task: TaskData){
        taskDao.AddTask(task)

    }

    suspend fun getUsersWithTeams(): List<UserWithTeams> {
        return teamDao.getUsersWithTeams()
    }

    suspend fun getTeamByCode(teamCode: String): TeamData?
    {
        return teamDao.getTeamByCode(teamCode)
    }






}