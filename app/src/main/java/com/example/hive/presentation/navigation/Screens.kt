package com.example.hive.presentation.navigation

sealed class Screens (val route: String) {
    object StartScreen : Screens("start")
    object SignUpScreen : Screens("signUp")
    object SignInScreen : Screens("signIn")
    object CreateProfileScreen : Screens("create_profile")
}