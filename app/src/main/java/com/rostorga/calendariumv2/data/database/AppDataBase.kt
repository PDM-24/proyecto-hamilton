package com.rostorga.calendariumv2.data.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rostorga.calendariumv2.data.database.dao.TeamDao
import com.rostorga.calendariumv2.data.database.dao.UserDao
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData


@Database(entities=[TeamData::class, UserData::class], version=1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    abstract fun UserDao(): UserDao



    companion object{
        private fun buildDataBase(context: Context): AppDataBase{
            return  Room.databaseBuilder(context, AppDataBase::class.java, "db").build()
        }
        @Volatile
        private var INSTSANCE: AppDataBase?=null

        fun getDataBaseInstance(context: Context): AppDataBase{
            if(INSTSANCE==null){
                synchronized(this){
                    INSTSANCE= buildDataBase(context)
                }

            }

            return INSTSANCE!!

        }
    }
}