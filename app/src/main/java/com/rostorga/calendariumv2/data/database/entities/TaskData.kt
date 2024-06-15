package com.rostorga.calendariumv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName="Tasks")
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int=0,

    @ColumnInfo(name="TaskName")
    val TaskName: String,

    @ColumnInfo(name="TaskDesc")
    val TaskDesc: String,

    @ColumnInfo(name="Date")
    val Date: String,

    @ColumnInfo(name="TimeStart")
    val TimeStart :String,

    @ColumnInfo(name="TimeFinish")
    val TimeFinishi: String,

    //this is the FK
    @ColumnInfo(name="PersonId")
    val PersonId: Int,





    )