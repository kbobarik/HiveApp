package com.example.hive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hive.presentation.CreateProfileScreen
import com.example.hive.presentation.LoginScreen
import com.example.hive.presentation.SignUpScreen
import com.example.hive.presentation.StartScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.StartScreen.route) {
        composable(Screens.StartScreen.route) {
            StartScreen(navController)
        }
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(Screens.SignInScreen.route) {
            LoginScreen()
        }
        composable(Screens.CreateProfileScreen.route) {
            CreateProfileScreen()
        }
    }
}