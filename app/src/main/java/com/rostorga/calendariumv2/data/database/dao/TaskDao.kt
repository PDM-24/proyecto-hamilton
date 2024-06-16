package com.rostorga.calendariumv2.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.UserData


@Dao
interface TaskDao{

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun AddTask(task : TaskData)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): LiveData<List<TaskData>>




}