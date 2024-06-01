package com.rostorga.calendariumv2.screens

sealed class Screen(val route:String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("singup_screen")
}