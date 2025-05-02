package com.pdmtaller2.O0085720_DiegoContreras.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import com.pdmtaller2.O0085720_DiegoContreras.homeScreen
import com.pdmtaller2.O0085720_DiegoContreras.menuScreen
import com.pdmtaller2.O0085720_DiegoContreras.restauranteScreen
import com.pdmtaller2.O0085720_DiegoContreras.ui.components.BottomNavigationBar
import com.pdmtaller2.O0085720_DiegoContreras.ui.Screens.Screens
import com.pdmtaller2.O0085720_DiegoContreras.ui.Screens.ordersScreen
import searchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.route) {
                homeScreen(onRestaurantClick = { id ->
                    navController.navigate("restaurant/$id")
                })
            }
            composable(Screens.Search.route) {
                searchScreen(
                    onBackClick = TODO()
                )
            }
            composable(Screens.Orders.route) {
                ordersScreen(
                    onBackClick = TODO()
                )
            }
            composable("restaurant/{restaurantId}") { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull() ?: 0
                restauranteScreen(
                    restaurantId = restaurantId,
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = {
                        navController.navigate("menu/$restaurantId")
                    }
                )
            }
            composable("menu/{restaurantId}") { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull() ?: 0
                menuScreen(
                    restaurantId = restaurantId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}