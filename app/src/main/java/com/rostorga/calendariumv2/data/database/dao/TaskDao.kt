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

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TaskData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun AddTask(task: TaskData)


}