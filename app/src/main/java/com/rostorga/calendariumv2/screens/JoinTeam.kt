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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rostorga.calendariumv2.objects.UserManager
import com.rostorga.calendariumv2.viewModel.ApiViewModel
import com.rostorga.calendariumv2.viewModel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun JoinTeam(
    onDismiss:()->Unit,
    userViewModel: UserViewModel,
    apiViewModel: ApiViewModel
) {

    val context = LocalContext.current

    var teamCode by remember { mutableStateOf(" ") }
    val users by userViewModel.getAllData.observeAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    val joinTeamUserId = UserManager.getUser()


    Dialog(onDismissRequest = { onDismiss() }){

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(600.dp)
                .height(200.dp)
                .background(Color(0xFFFFC64B)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier
                    .background(Color.White)
                    .width(20.dp)
                    .fillMaxHeight().clip(RoundedCornerShape(20.dp)))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Insert the code here!", color = Color.White, fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = teamCode,
                        onValueChange = { teamCode = it },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(50.dp),
                        placeholder = {
                            Text(text=" Ex: 123456 ", color=Color.Gray)
                        }

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            try{
                                if (teamCode.isNotBlank()) {
                                scope.launch {
                                    apiViewModel.apiJoinTeam(joinTeamUserId.toString(), teamCode.toString())
                                    Toast.makeText(context, "Successfully joined the team!", Toast.LENGTH_SHORT).show()

                                }
                            } else {
                                Toast.makeText(context, "Please enter a valid team code.", Toast.LENGTH_SHORT).show()
                            }}
                            catch(e:Exception){
                                Log.d("jointeam", "cannot join team", e)

                            }

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Join!", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier
                    .background(Color.White)
                    .width(20.dp)
                    .fillMaxHeight())
            }
        }

    }
}

