package com.rostorga.calendariumv2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rostorga.calendariumv2.screens.JoinTeam
import com.rostorga.calendariumv2.screens.CreateTeam



@Composable
fun CreateOrJoinTeam(

    onDismiss:()->Unit

) {

    var showJoinTeam by remember { mutableStateOf(false) }

    if (showJoinTeam) {
        JoinTeam(onDismiss = { showJoinTeam = false })
    }


    var showCreateTeam by remember{ mutableStateOf(false)}

    if (showCreateTeam) {
        CreateTeam(onDismiss={showCreateTeam=false})

    }


    Dialog(onDismissRequest = { onDismiss() }){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(600.dp)
                .height(300.dp)
                .background(Color(0xFFFFC64B))
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Want to create a team?", color = Color.White, fontSize = 20.sp)
                    Button(
                        onClick = { showCreateTeam=true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Create!", color = Color.Black)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Want to join a team?", color = Color.White, fontSize = 20.sp)
                    Button(
                        onClick = { showJoinTeam=true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Join!", color = Color.Black)
                    }
                }
            }

        }

    }

}


//this is the code to just preview without deleting dialog so that
//I can edit it later

/*        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(600.dp)
                .height(300.dp)
                .background(Color(0xFFFFC64B))
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Want to create a team?", color = Color.White, fontSize = 20.sp)
                    Button(
                        onClick = {/*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Create!", color = Color.Black)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Want to join a team?", color = Color.White, fontSize = 20.sp)
                    Button(
                        onClick = {/*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Join!", color = Color.Black)
                    }
                }
            }

        }*/