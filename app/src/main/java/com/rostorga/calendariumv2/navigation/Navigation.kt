package com.rostorga.calendariumv2.navigation
import ViewContainer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rostorga.calendariumv2.screens.LoginScreen
import com.rostorga.calendariumv2.screens.Screen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.MainScreen.route){
            ViewContainer(navController = navController)
        }
        composable(route = Screen.LoginScreen.route   ){
            LoginScreen(navController = navController)
        }
    }
}
