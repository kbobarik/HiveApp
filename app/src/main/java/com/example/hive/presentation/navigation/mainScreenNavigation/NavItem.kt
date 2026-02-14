package com.example.hive.presentation.navigation.mainScreenNavigation

import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.example.hive.R
import com.example.hive.presentation.navigation.Screens
import javax.inject.Inject

sealed class NavItem (
    val label: String,
    val icon: Int,
    val rote: String
) {
    object Chats: NavItem(
        label = "Chats",
        icon = R.drawable.chats ,
        rote = Screens.AllChatsScreen.route

    )
    object People: NavItem(
        label = "People",
        icon = R.drawable.people,
        rote = Screens.AllUsersScreen.route

    )
    object Profile: NavItem(
        label = "Profile",
        icon = R.drawable.profile,
        rote = Screens.AllChatsScreen.route

    )
}