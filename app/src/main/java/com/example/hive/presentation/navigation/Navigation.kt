package com.example.hive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hive.presentation.allChats.AllChatsScreen
import com.example.hive.presentation.createProfile.CreateProfileScreen
import com.example.hive.presentation.login.LoginScreen
import com.example.hive.presentation.navigation.mainScreenNavigation.AppNavigation
import com.example.hive.presentation.singUp.SignUpScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SignInScreen.route) {
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(Screens.SignInScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.CreateProfileScreen.route) {
            CreateProfileScreen(navController)
        }
        composable(Screens.MainScreens.route) {
            AppNavigation(navController)
        }
    }
}