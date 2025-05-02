package com.pdmtaller2.O0085720_DiegoContreras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pdmtaller2.O0085720_DiegoContreras.ui.Screens.ordersScreen
import com.pdmtaller2.O0085720_DiegoContreras.ui.components.BottomNavigationBar
import com.pdmtaller2.O0085720_DiegoContreras.ui.components.Screen
import searchScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RestaurantApp()
                }
            }
        }
    }
}

@Composable
fun RestaurantApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                homeScreen(
                    onRestaurantClick = { restaurantId ->
                        navController.navigate("restaurant/$restaurantId")
                    }
                )
            }
            composable(Screen.Search.route) {
                searchScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Orders.route) {
                ordersScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("restaurant/{restaurantId}") { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull() ?: 0
                restauranteScreen(
                    restaurantId = restaurantId,
                    onBackClick = { navController.popBackStack() },
                    onMenuClick = { id ->
                        navController.navigate("menu/$id")
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

@Composable
fun RestaurantAppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}