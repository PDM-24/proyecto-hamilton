// UserScreen.kt

package com.rostorga.calendariumv2.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData
import com.rostorga.calendariumv2.viewModel.UserViewModel

@Composable
fun UserScreen(userViewModel: UserViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLeader by remember { mutableStateOf(false) }
    var teamName by remember { mutableStateOf("") }
    var teamCode by remember { mutableStateOf("") }
    var joinTeamCode by remember { mutableStateOf("") }
    var showUsers by remember { mutableStateOf(false) }
    val users by userViewModel.getAllData.observeAsState(initial = emptyList())
    val usersWithTeams by userViewModel.usersWithTeams.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isLeader,
                onCheckedChange = { isLeader = it }
            )
            Text(text = "Is Leader")
        }
        Button(
            onClick = {
                try {
                    val user = UserData(
                        name = name,
                        lastName = lastName,
                        Username = username,
                        Password = password,
                        isLeader = isLeader
                    )
                    userViewModel.addUser(user)
                    Log.d("UserScreen", "User added: $user")
                } catch (e: Exception) {
                    Log.e("UserScreen", "Error adding user", e)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = teamName,
            onValueChange = { teamName = it },
            label = { Text("Team Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = teamCode,
            onValueChange = { teamCode = it },
            label = { Text("Team Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                try {
                    if (users.isNotEmpty()) {
                        // Assuming the first user is creating the team, adjust as needed
                        val user = users.first()
                        val team = TeamData(
                            teamName = teamName,
                            teamCode = teamCode,
                            PersonId = user.id
                        )
                        userViewModel.addTeam(team)
                        Log.d("UserScreen", "Team added: $team")
                    }
                } catch (e: Exception) {
                    Log.e("UserScreen", "Error adding team", e)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Team")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = joinTeamCode,
            onValueChange = { joinTeamCode = it },
            label = { Text("Join Team Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                try {
                    if (users.isNotEmpty()) {
                        // Assuming the first user is joining the team, adjust as needed
                        val user = users.first()
                        userViewModel.joinTeam(user.id, joinTeamCode)
                        Log.d("UserScreen", "User joined team with code: $joinTeamCode")
                    }
                } catch (e: Exception) {
                    Log.e("UserScreen", "Error joining team", e)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Join Team")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                showUsers = !showUsers
                if (showUsers) {
                    userViewModel.fetchUsersWithTeams()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (showUsers) "Hide Users and Teams" else "Display Users and Teams")
        }
        if (showUsers) {
            LazyColumn {
                items(usersWithTeams) { userWithTeams ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(text = "User: ${userWithTeams.user.name} ${userWithTeams.user.lastName}")
                        Text(text = "Username: ${userWithTeams.user.Username}")
                        userWithTeams.teams.forEach { team ->
                            Text(text = "In Team: ${team.teamName}")
                        }
                    }
                }
            }
        }
    }
}
