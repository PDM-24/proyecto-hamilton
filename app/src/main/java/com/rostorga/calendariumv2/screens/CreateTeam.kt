package com.rostorga.calendariumv2.screens

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rostorga.calendariumv2.data.database.entities.TeamData
import com.rostorga.calendariumv2.viewModel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun CreateTeam(
    onDismiss:()->Unit,
    userViewModel: UserViewModel
){


    var teamName by remember { mutableStateOf(TextFieldValue("")) }
    var teamCode by remember { mutableStateOf(TextFieldValue("")) }


    val usersWithTeams by userViewModel.usersWithTeams.observeAsState(emptyList())
    val users by userViewModel.getAllData.observeAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = { onDismiss() }){


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

                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .padding(4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
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
                            Text(text=" Ex: 123456 ", color= Color.White)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    OutlinedTextField(
                        value = teamCode,
                        onValueChange = { teamCode = it },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(64.dp),
                        placeholder = {
                            Text(text=" Ex: 123456 ", color= Color.White)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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
                        modifier = Modifier.width(200.dp)

                    ) {
                        Text("Create Team")

                    }

                }

            }

        }

    }





}

@Preview(showBackground = true)
@Composable
fun PreviewCreateTeam() {
    CreateTeam(onDismiss = {}, userViewModel = UserViewModel(application = Application()))
}
