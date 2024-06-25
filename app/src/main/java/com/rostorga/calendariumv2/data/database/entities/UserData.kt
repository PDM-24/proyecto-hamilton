package com.rostorga.calendariumv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "lastName")
    val lastName: String,

    @ColumnInfo(name = "Username")
    val Username: String,

    @ColumnInfo(name = "Password")
    val Password: String,
)
