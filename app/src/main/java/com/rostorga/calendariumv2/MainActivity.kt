package com.rostorga.calendariumv2


import CalendarFAB
import CalendarScreen
import CalendarScreenContainer
import ViewContainer
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rostorga.calendariumv2.screens.LoginScreen
import com.rostorga.calendariumv2.screens.Screen
import com.rostorga.calendariumv2.ui.UserScreen
import com.rostorga.calendariumv2.ui.theme.Calendariumv2Theme
import com.rostorga.calendariumv2.viewModel.UserViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calendariumv2Theme {
                MyApp()

            }

        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(navController, startDestination = Screen.LoginScreen.route) {
        composable("home") {
            ViewContainer(navController)
        }
        composable("calendar") {
            CalendarScreenContainer(navController, userViewModel)
        }
        composable(route = Screen.LoginScreen.route   ){
            LoginScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenContainer(navController: NavHostController, userViewModel: UserViewModel) {
    Scaffold(
        topBar={
            TopAppBar(title = {
                Text(text="Calendar")
            },
                navigationIcon = { IconButton(onClick = {navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }}
            )
        },
        content = {
            CalendarScreen(navController = navController, userViewModel = userViewModel)
        },
        floatingActionButton = { CalendarFAB() },
        floatingActionButtonPosition = FabPosition.End
    )
}