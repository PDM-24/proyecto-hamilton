package com.rostorga.calendariumv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName="Teams", foreignKeys = [
    ForeignKey(entity=UserData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("PersonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
])
data class TeamData(
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="TeamId")
    val teamId: Int=0,

    @ColumnInfo(name="TeamName")
    val teamName: String,

    @ColumnInfo(name="TeamCode")
    val teamCode: String,

    //this is the FK
    @ColumnInfo(name="PersonId")
    val PersonId: Int




)
