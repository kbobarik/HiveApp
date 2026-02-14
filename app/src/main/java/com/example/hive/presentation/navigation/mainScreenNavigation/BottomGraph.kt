package com.example.hive.presentation.navigation.mainScreenNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hive.presentation.LoadingScreen
import com.example.hive.presentation.allChats.AllChatsScreen
import com.example.hive.presentation.allUsers.AllUsersScreen
import com.example.hive.presentation.navigation.Screens

@Composable
fun BottomGraph (
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.AllChatsScreen.route,
    ) {
        composable(Screens.AllChatsScreen.route) {
            AllChatsScreen()
        }
        composable(Screens.LoadingScreen.route) {
            LoadingScreen()
        }
        composable(Screens.AllUsersScreen.route) {
            AllUsersScreen()
        }
    }
}