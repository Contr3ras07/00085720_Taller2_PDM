package com.pdmtaller2.O0085720_DiegoContreras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun restauranteScreen(
    restaurantId: Int,
    onBackClick: () -> Unit,
    onMenuClick: (Int) -> Unit
) {
    val restaurant = getSampleRestaurant(restaurantId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = restaurant.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF00BCD4),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE1F5FE))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    RestaurantHeader(restaurant = restaurant)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onMenuClick(restaurant.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64FFDA))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.Black
                        )
                        Text(text = "Ver Menú Completo", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "Platos Destacados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                val highlightedDishes = restaurant.menu.take(3)
                items(highlightedDishes) { dish ->
                    DishItem(dish = dish)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Categorías",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(restaurant.categories) { category ->
                    CategoryChip(category = category)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun RestaurantHeader(restaurant: Restaurant) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = restaurant.imageUrl),
            contentDescription = restaurant.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = restaurant.description,
            fontSize = 16.sp,
            color = Color(0xFF424242),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun CategoryChip(category: String) {
    SuggestionChip(
        onClick = {  },
        label = {
            Text(
                text = category,
                color = Color(0xFF00796B)
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color(0xFFB2EBF2)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun DishItem(dish: Dish) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = dish.imageUrl),
                contentDescription = dish.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dish.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF00796B)
                )

                Text(
                    text = dish.description,
                    fontSize = 14.sp,
                    color = Color(0xFF616161)
                )
            }
        }
    }
}

