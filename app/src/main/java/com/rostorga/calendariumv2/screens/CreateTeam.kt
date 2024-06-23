package com.rostorga.calendariumv2.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rostorga.calendariumv2.api.apiObject.TeamApiObject
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.viewModel.ApiViewModel
import com.rostorga.calendariumv2.viewModel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTeam(
    onDismiss: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    apiViewModel: ApiViewModel = viewModel()
) {
    var teamName by remember { mutableStateOf(TextFieldValue("")) }
    var teamCode by remember { mutableStateOf(TextFieldValue("")) }

    val usersWithTeams by userViewModel.usersWithTeams.observeAsState(emptyList())
    val users by userViewModel.getAllData.observeAsState(initial = emptyList())
    val currentUserId by apiViewModel.currentUserId.observeAsState()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(300.dp)
                .height(350.dp)
                .background(Color(0xFFFFC64B)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(color = Color.White)
                        )
                        Text(text = "Lets give it a name!", color = Color.White, fontSize = 20.sp)
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(color = Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = teamName,
                        onValueChange = { teamName = it },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(64.dp),
                        placeholder = {
                            Text(text = " Ex: Best Team Ever! ", color = Color.White)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = teamCode,
                        onValueChange = { teamCode = it },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(64.dp),
                        placeholder = {
                            Text(text = " Ex: 123456 ", color = Color.White)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    if (users.isNotEmpty() && currentUserId !=null) {
                                        val user = users.first()
                                        val team = TeamData(
                                            teamName = teamName.text,
                                            teamCode = teamCode.text,
                                            PersonId = user.id
                                        )
                                        userViewModel.addTeam(team)
                                        Log.d("CreateTeam", "Team added: $team")

                                        // Creating TeamApiObject with the current user's ID as the leader
                                        val apiTeam = TeamApiObject(
                                            name = teamName.text,
                                            code = teamCode.text,
                                            leader = currentUserId.toString() // Using the current user's ID
                                        )
                                        apiViewModel.postTeam(apiTeam)

                                    }
                                } catch (e: Exception) {
                                    Log.e("CreateTeam", "Error adding team", e)
                                }
                            }
                        },
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text("Create Team")
                    }
                }
            }
        }
    }
}
