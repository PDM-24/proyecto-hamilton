package com.rostorga.calendariumv2.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rostorga.calendariumv2.R
import com.rostorga.calendariumv2.viewModel.UserViewModel

//IMPORTANT
/*
    onDismiss:()-> Unit

            AlertDialog(onDismissRequest = onDismiss, confirmButton = {
                Button(onClick = onDismiss)
                {
                    Text(text = "OK")
                }
            }
            )*/

@Composable
fun profileScreen(onDismiss:()-> Unit,
                  image: Painter,
                //  navController: NavController


) {
    var username by remember { mutableStateOf(" ") }
    var email by remember { mutableStateOf(" ") }
    var teamName by remember { mutableStateOf(" ") }
    var teamCode by remember { mutableStateOf(" ") }
    var Password by remember { mutableStateOf(" ") }





    Dialog(onDismissRequest = { onDismiss() }

        ){
        Box(
            modifier = Modifier
                .height(900.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(900.dp)
                        .width(350.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(color = Color(0xFF6200EE))
                ) {
                    Box(
                        modifier = Modifier
                            .height(650.dp)
                            .width(350.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(color = Color.White)
                            .align(Alignment.BottomCenter)
                    ) {

                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Text(text = "Username", modifier = Modifier.padding(12.dp))
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .padding(7.dp),
                                shape = RoundedCornerShape(15.dp)
                            )
                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(text = "Email", modifier = Modifier.padding(12.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .padding(7.dp),
                                shape = RoundedCornerShape(15.dp)
                            )

                            Row(modifier = Modifier.padding(12.dp)) {
                                Column {
                                    Text(text = "Team Name", modifier = Modifier.padding(12.dp))
                                    OutlinedTextField(
                                        value = teamName,
                                        onValueChange = { teamName = it },
                                        modifier = Modifier
                                            .height(65.dp)
                                            .width(120.dp)
                                            .padding(7.dp),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(text = "Team Code", modifier = Modifier.padding(12.dp))
                                    OutlinedTextField(
                                        value = teamCode,
                                        onValueChange = { teamCode = it },
                                        modifier = Modifier
                                            .height(65.dp)
                                            .fillMaxWidth()
                                            .padding(7.dp),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(text = "Password", modifier = Modifier.padding(12.dp))
                            OutlinedTextField(
                                value = Password,
                                onValueChange = { Password = it },
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp)
                            )
                            Spacer(modifier = Modifier.padding(2.dp))

                            Text(text = "Password", modifier = Modifier.padding(12.dp))
                            OutlinedTextField(
                                value = Password,
                                onValueChange = { Password = it },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(15.dp)
                            )
                            Spacer(modifier = Modifier.padding(2.dp))

                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(125.dp)
                    .offset(y = -320.dp)
                    .clip(CircleShape)
                    .background(color = Color.Red)
                    .align(Alignment.Center)
            ){
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.size(125.dp)
                )
            }

        }


    }



}

