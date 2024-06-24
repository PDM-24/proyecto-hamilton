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
    userId: String,  // Ensure this parameter is accepted
    onDismiss: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    apiViewModel: ApiViewModel = viewModel()
) {
    var teamName by remember { mutableStateOf(TextFieldValue("")) }
    var teamCode by remember { mutableStateOf(TextFieldValue("")) }

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
                    Text(text = "Let's give it a name!", color = Color.White, fontSize = 20.sp)
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
                                if (teamName.text.isNotBlank() && teamCode.text.isNotBlank() && currentUserId != null) {
                                    // Create TeamApiObject with current user's ID as the leader
                                    val apiTeam = TeamApiObject(
                                        name = teamName.text,
                                        code = teamCode.text,
                                        leader = currentUserId!! // Assuming 'leader' is a String. Adjust if necessary
                                    )
                                    apiViewModel.postTeam(apiTeam)
                                } else {
                                    Toast.makeText(context, "Please ensure all fields are filled and you are logged in.", Toast.LENGTH_LONG).show()
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