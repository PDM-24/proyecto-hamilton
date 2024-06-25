package com.rostorga.calendariumv2.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserWithTeams

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTeam(team: TeamData)

    @Transaction
    @Query("SELECT * FROM User INNER JOIN Teams on User.id= Teams.PersonId")
    fun getUsersWithTeams(): List<UserWithTeams>

    @Query("SELECT * FROM Teams WHERE teamCode= :teamCode")
    suspend fun getTeamByCode(teamCode: String): TeamData?

}