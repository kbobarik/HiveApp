package com.example.hive.presentation.navigation

sealed class Screens (val route: String) {
    object StartScreen : Screens("start")
    object SignUpScreen : Screens("signUp")
    object SignInScreen : Screens("signIn")
    object CreateProfileScreen : Screens("create_profile")
    object AllChatsScreen : Screens("all_chats")
    object MainScreens : Screens("main_screens")
    object LoadingScreen : Screens("loading")
    object AllUsersScreen : Screens("all_users")
}