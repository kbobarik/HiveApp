package com.example.hive.presentation.navigation.mainScreenNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hive.ui.theme.BlueDarkest
import com.example.hive.ui.theme.GreyLight
import com.example.hive.ui.theme.Nimbus

@Composable
fun AppNavigation(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent) ,
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            BottomGraph(navController = navController)
        }
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavItem.Chats,
        NavItem.People,
        NavItem.Profile
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .height(88.dp)
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}
@Composable
fun AddItem(
    screen: NavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.rote } == true

    val background =
        if (selected) Color.White else Color.Transparent

    val contentColor =
        if (selected) BlueDarkest else GreyLight

    Box(
        modifier = Modifier
            .width(85.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.rote) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Column(
            modifier = Modifier
                .height(88.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "icon",
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = screen.label,
                color = contentColor,
                fontSize = 12.sp,
                fontFamily = Nimbus,
                fontWeight = FontWeight.Normal
            )
        }
    }
}