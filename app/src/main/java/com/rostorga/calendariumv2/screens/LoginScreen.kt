package com.rostorga.calendariumv2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rostorga.calendariumv2.R
import com.rostorga.calendariumv2.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {

    var userName by remember { mutableStateOf(TextFieldValue(""))}
    var password by remember { mutableStateOf(TextFieldValue(""))}

    Column(
        modifier = Modifier
            .background(BackgroundPurple)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ribbon_2), contentDescription = "purple ribbon"
        )

        Text(text = "Login",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp))
            {
                Image(painter = painterResource(id = R.drawable.user_icon), contentDescription = "Profile Icon")
                inputField(output = userName, onClick = {userName = it}, placeholderArg = "Username")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp))
            {
                Image(painter = painterResource(id = R.drawable.lock_icon), contentDescription = "Profile Icon")
                inputField(output = password, onClick = {password = it}, placeholderArg = "PassWord")
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = { navController.navigate(Screen.MainScreen.route)}) {
            Text(text = "CLICK ME")
        }

        Image(
            painter = painterResource(id = R.drawable.ribbon_small), contentDescription = "purple ribbon" ,
            modifier = Modifier
                    .align(Alignment.End),
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputField(
    output: TextFieldValue,
    onClick: (TextFieldValue) -> Unit,
    placeholderArg: String = "",
    modifier: Modifier = Modifier
) {
    TextField(
        value = output,
        onValueChange = onClick,
        textStyle = TextStyle(
            fontSize = 13.sp
        ),
        modifier = Modifier
            .width(235.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(color = DarkBlue),
        placeholder = {Text( text = placeholderArg, color = Gray, fontSize = 13.sp)},
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors( focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
    )
}