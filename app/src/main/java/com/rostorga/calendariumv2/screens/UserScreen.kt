package com.rostorga.calendariumv2.ui

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rostorga.calendariumv2.api.apiObject.UserApiObject
import com.rostorga.calendariumv2.api.apiObject.UserNameApiObject
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.data.database.entities.UserData
import com.rostorga.calendariumv2.ui.theme.BackgroundPurple
import com.rostorga.calendariumv2.viewModel.UserViewModel
import com.rostorga.calendariumv2.viewModel.ApiViewModel
import kotlinx.coroutines.launch


//this is a temp screen to make sure the data is sent and the localDB is setup

@Composable
fun UserScreen(navController: NavController,userViewModel: UserViewModel = viewModel()) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isLeader by remember { mutableStateOf(false) }
    var teamName by remember { mutableStateOf(TextFieldValue("")) }
    var teamCode by remember { mutableStateOf(TextFieldValue("")) }
    var joinTeamCode by remember { mutableStateOf(TextFieldValue("")) }
    var showUsers by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf(TextFieldValue("")) }
    var taskDesc by remember { mutableStateOf(TextFieldValue("")) }
    var taskDate by remember { mutableStateOf(TextFieldValue("")) }
    var taskTimeStart by remember { mutableStateOf(TextFieldValue("")) }
    var taskTimeFinish by remember { mutableStateOf(TextFieldValue("")) }

    val usersWithTeams by userViewModel.usersWithTeams.observeAsState(emptyList())
    val users by userViewModel.getAllData.observeAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = lastName ,
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
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isLeader,
                    onCheckedChange = { isLeader = it }
                )
                Text(text = "Is Leader")
            }
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val user = UserData(
                                name = name.text,
                                lastName = lastName.text,
                                Username = username.text,
                                Password = password.text
                            )
                            userViewModel.addUser(user)
                            Log.d("UserScreen", "User added: $user")
                        } catch (e: Exception) {
                            Log.e("UserScreen", "Error adding user", e)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add User")
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
                    scope.launch {
                        try {
                            if (users.isNotEmpty()) {
                                val user = users.first()
                                val team = TeamData(
                                    teamName = teamName.text,
                                    teamCode = teamCode.text,
                                    PersonId = user.id
                                )
                                userViewModel.addTeam(team)
                                Log.d("UserScreen", "Team added: $team")
                            }
                        } catch (e: Exception) {
                            Log.e("UserScreen", "Error adding team", e)
                        }
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
                    scope.launch {
                        try {
                            if (users.isNotEmpty()) {
                                val user = users.first()
                                userViewModel.joinTeam(user.id, joinTeamCode.text)
                                Log.d("UserScreen", "User joined team with code: $joinTeamCode")
                            }
                        } catch (e: Exception) {
                            Log.e("UserScreen", "Error joining team", e)
                        }
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
        }
        if (showUsers) {
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
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = taskName,
                onValueChange = { taskName = it },
                label = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = taskDesc,
                onValueChange = { taskDesc = it },
                label = { Text("Task Description") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = taskDate,
                onValueChange = { taskDate = it },
                label = { Text("Task Date") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = taskTimeStart,
                onValueChange = { taskTimeStart = it },
                label = { Text("Task Start Time") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = taskTimeFinish,
                onValueChange = { taskTimeFinish = it },
                label = { Text("Task Finish Time") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    scope.launch {
                        try {
                            if (users.isNotEmpty()) {
                                val user = users.first()
                                val task = TaskData(
                                    TaskName = taskName.text,
                                    TaskDesc = taskDesc.text,
                                    Date = taskDate.text,
                                    TimeStart = taskTimeStart.text,
                                    TimeFinish = taskTimeFinish.text,
                                    PersonId = user.id
                                )

                                userViewModel.addTask(task)
                                Log.d("UserScreen", "Task added: $task")
                            }
                        } catch (e: Exception) {
                            Log.e("UserScreen", "Error adding task", e)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }
        }
    }
}




@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel = viewModel(), apiViewModel: ApiViewModel = viewModel()) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isLeader by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val registrationResponse by apiViewModel.loginResponse.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize().background(BackgroundPurple),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="Register", color=Color.White, fontSize = 32.sp)
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isLeader,
                onCheckedChange = { isLeader = it }
            )
            Text(text = "Is Leader", color = Color.White)
        }
        Button(
            onClick = {

                scope.launch{
                    val localuser = UserData(
                        name = name.text,
                        lastName = lastName.text,
                        Username = username.text,
                        Password = password.text)
                    userViewModel.addUser(localuser)
                    Log.d("UserScreen", "User added: $localuser")
                }

                scope.launch {
                    val user = UserApiObject(
                        Name = UserNameApiObject(name.text, lastName.text),
                        UserName = username.text,
                        Password = password.text
                    )
                    apiViewModel.postUser(user)

                }
            },
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))

        registrationResponse?.let {
            Text(it)
        }
    }
}