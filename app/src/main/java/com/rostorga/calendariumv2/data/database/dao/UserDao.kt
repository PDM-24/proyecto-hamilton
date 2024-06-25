package com.rostorga.calendariumv2.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rostorga.calendariumv2.data.database.entities.UserData

@Dao
interface UserDao{

@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun addUser(user: UserData)


@Query("SELECT * FROM User")
fun getAllUsers(): LiveData<List<UserData>>






}