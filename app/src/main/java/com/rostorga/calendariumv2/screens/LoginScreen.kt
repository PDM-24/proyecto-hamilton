package com.rostorga.calendariumv2.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rostorga.calendariumv2.R
import com.rostorga.calendariumv2.viewModel.ApiViewModel
import com.rostorga.calendariumv2.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen(navController: NavController) {

    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val api: ApiViewModel = viewModel()
    val context = LocalContext.current

    val loginResponse by api.loginResponse.observeAsState()

    Column(
        modifier = Modifier
            .background(BackgroundPurple)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ribbon_2), contentDescription = "purple ribbon"
        )

        Text(
            text = "Login",
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Image(painter = painterResource(id = R.drawable.user_icon), contentDescription = "Profile Icon")
                inputField(output = userName, onClick = { userName = it }, placeholderArg = "Username")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Image(painter = painterResource(id = R.drawable.lock_icon), contentDescription = "Profile Icon")
                inputField(output = password, onClick = { password = it }, placeholderArg = "Password")
            }
            Text(
                text = "Don't have an account yet? Register now!",
                color = Color.White,
                modifier = Modifier.clickable(onClick = { navController.navigate("register") })
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                api.loginUser(userName.text, password.text)
            }) {
                Text(text = "Login")
            }

            loginResponse?.let { response ->
                if (response == "Login successful!") {
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ribbon_small), contentDescription = "purple ribbon",
            modifier = Modifier.align(Alignment.End),
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
        textStyle = TextStyle(fontSize = 13.sp),
        modifier = Modifier
            .width(235.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(color = DarkBlue),
        placeholder = { Text(text = placeholderArg, color = Gray, fontSize = 13.sp) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
