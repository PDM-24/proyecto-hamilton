package com.rostorga.calendariumv2.screens

import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import com.rostorga.calendariumv2.api.ApiClient
import com.rostorga.calendariumv2.api.ApiService
import com.rostorga.calendariumv2.api.apiObject.TeamApiObject
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.objects.UserManager
import com.rostorga.calendariumv2.viewModel.ApiViewModel
import com.rostorga.calendariumv2.viewModel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random



@SuppressLint("RememberReturnType")
@Composable
fun CreateTeam(
    onDismiss: () -> Unit,
    apiViewModel: ApiViewModel = viewModel(),
    navController: NavController
) {
    var teamName by remember { mutableStateOf(TextFieldValue("")) }
    var teamCode by remember { mutableStateOf("") } // Now this will update based on LiveData changes

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Retrieve the user ID from UserManager
    val userId = UserManager.getUser()




    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(300.dp)
                .height(350.dp)
                .background(Color(0xFFFFC64B)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Let's give it a name!", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("Team Name") },
                    placeholder = { Text(text = " Ex: Best Team Ever! ", color = Color.White) }
                )
                if (teamCode.isNotEmpty()) {
                    Text("Team Code: $teamCode")  // Display fetched code
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        Log.d("CreateTeam", "Team Name: ${teamName.text}, Team Code: $teamCode")

                        if (teamName.text.isNotEmpty()) {
                                val team = TeamApiObject(
                                    name = teamName.text,
                                    leader = UserManager.getUser() ?: "")
                                ApiViewModel().postTeam(team)

                            navController.navigate("teamHomeScreen")
                        } else {
                            Toast.makeText(context, "Please ensure all fields are filled.", Toast.LENGTH_LONG).show()
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