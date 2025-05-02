package com.pdmtaller2.O0085720_DiegoContreras.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Orders : Screen("orders")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem(
            title = "Restaurantes",
            route = Screen.Home.route,
            icon = Icons.Filled.Dehaze
        ),
        NavigationItem(
            title = "Buscar",
            route = Screen.Search.route,
            icon = Icons.Filled.Search
        ),
        NavigationItem(
            title = "Mis Órdenes",
            route = Screen.Orders.route,
            icon = Icons.Filled.History
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector // Ahora usas ImageVector en vez de iconResourceId
)
