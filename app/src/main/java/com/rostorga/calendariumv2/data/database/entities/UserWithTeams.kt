package com.rostorga.calendariumv2.data.database.entities


//es para crear la relacino entre ellos

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTeams(
    @Embedded val user: UserData,
    @Relation(
        parentColumn = "id",
        entityColumn = "PersonId"
    )
    val teams: List<TeamData>
)
